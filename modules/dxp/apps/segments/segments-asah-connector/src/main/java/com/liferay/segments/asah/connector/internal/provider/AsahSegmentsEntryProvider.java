/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.constants.SegmentsAsahDestinationNames;
import com.liferay.segments.asah.connector.internal.context.contributor.SegmentsAsahRequestContextContributor;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 * @author Eduardo García
 */
@Component(
	property = {
		"segments.entry.provider.order:Integer=50",
		"segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND
	},
	service = SegmentsEntryProvider.class
)
public class AsahSegmentsEntryProvider implements SegmentsEntryProvider {

	@Override
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException {

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				segmentsEntryId, start, end, null);

		Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

		return stream.mapToLong(
			SegmentsEntryRel::getClassPK
		).toArray();
	}

	@Override
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException {

		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			segmentsEntryId);
	}

	@Override
	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException {

		return getSegmentsEntryIds(
			groupId, className, classPK, context, new long[0], new long[0]);
	}

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, String className, long classPK, Context context,
		long[] filterSegmentsEntryIds, long[] segmentsEntryIds) {

		if (context == null) {
			return new long[0];
		}

		if (GetterUtil.getBoolean(context.get(Context.SIGNED_IN))) {
			List<SegmentsEntry> segmentsEntries =
				_segmentsEntryLocalService.getSegmentsEntries(
					groupId, true,
					SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND, className,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			if (segmentsEntries.isEmpty()) {
				return new long[0];
			}

			Stream<SegmentsEntry> stream = segmentsEntries.stream();

			return stream.filter(
				segmentsEntry ->
					ArrayUtil.isEmpty(filterSegmentsEntryIds) ||
					ArrayUtil.contains(
						filterSegmentsEntryIds,
						segmentsEntry.getSegmentsEntryId())
			).filter(
				segmentsEntry ->
					_segmentsEntryRelLocalService.hasSegmentsEntryRel(
						segmentsEntry.getSegmentsEntryId(),
						_portal.getClassNameId(className), classPK)
			).sorted(
				(segmentsEntry1, segmentsEntry2) -> {
					Date modifiedDate = segmentsEntry2.getModifiedDate();

					return modifiedDate.compareTo(
						segmentsEntry1.getModifiedDate());
				}
			).mapToLong(
				SegmentsEntry::getSegmentsEntryId
			).toArray();
		}

		String userId = GetterUtil.getString(
			context.get(
				SegmentsAsahRequestContextContributor.
					KEY_SEGMENTS_ANONYMOUS_USER_ID));

		if (Validator.isNull(userId)) {
			return new long[0];
		}

		long[] cachedSegmentsEntryIds =
			_asahSegmentsEntryCache.getSegmentsEntryIds(userId);

		if (cachedSegmentsEntryIds == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Asah segments cache not found for user ID " + userId);
			}

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group != null) {
				_sendMessage(group.getCompanyId(), userId);
			}

			return new long[0];
		}

		return cachedSegmentsEntryIds;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				SegmentsAsahDestinationNames.INDIVIDUAL_SEGMENTS);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	private void _sendMessage(long companyId, String userId) {
		Message message = new Message();

		message.put("companyId", companyId);
		message.put("userId", userId);

		_messageBus.sendMessage(
			SegmentsAsahDestinationNames.INDIVIDUAL_SEGMENTS, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSegmentsEntryProvider.class);

	@Reference
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}