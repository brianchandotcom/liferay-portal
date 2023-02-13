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

package com.liferay.segments.asah.connector.internal.criteria.contributor;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.segments.asah.connector.internal.criteria.mapper.SegmentsCriteriaJSONObjectMapperImpl;
import com.liferay.segments.asah.connector.internal.odata.entity.EventEntityModel;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;
import com.liferay.segments.field.Field;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = EventSegmentsCriteriaContributor.class)
public class EventSegmentsCriteriaContributor
	implements SegmentsCriteriaContributor {

	public static final String KEY = "event";

	@Override
	public JSONObject getCriteriaJSONObject(Criteria criteria)
		throws Exception {

		SegmentsCriteriaJSONObjectMapper segmentsCriteriaJSONObjectMapper =
			new SegmentsCriteriaJSONObjectMapperImpl();

		return segmentsCriteriaJSONObjectMapper.toJSONObject(criteria, this);
	}

	@Override
	public EntityModel getEntityModel() {
		return _entityModel;
	}

	@Override
	public String getEntityName() {
		return EventEntityModel.NAME;
	}

	@Override
	public List<Field> getFields(PortletRequest portletRequest) {
		return Collections.singletonList(
			new Field(
				"downloadDocumentsAndMedia",
				_language.get(
					_portal.getLocale(portletRequest),
					"downloaded-document-and-media"),
				"event", null, _getSelectEntity(portletRequest)));
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Criteria.Type getType() {
		return Criteria.Type.ANALYTICS;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-171722"))) {
			_serviceRegistration = bundleContext.registerService(
				SegmentsCriteriaContributor.class, this,
				HashMapDictionaryBuilder.<String, Object>put(
					"segments.criteria.contributor.key",
					EventSegmentsCriteriaContributor.KEY
				).put(
					"segments.criteria.contributor.model.class.name", "*"
				).build());
		}
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private Field.SelectEntity _getSelectEntity(PortletRequest portletRequest) {
		try {
			FileItemSelectorCriterion fileItemSelectorCriterion =
				new FileItemSelectorCriterion();

			fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				new FileEntryItemSelectorReturnType());

			return new Field.SelectEntity(
				"selectEntity", "Select",
				PortletURLBuilder.create(
					_itemSelector.getItemSelectorURL(
						RequestBackedPortletURLFactoryUtil.create(
							portletRequest),
						"selectEntity", fileItemSelectorCriterion)
				).buildString(),
				true);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EventSegmentsCriteriaContributor.class);

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + EventEntityModel.NAME + ")"
	)
	private volatile EntityModel _entityModel;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private volatile ServiceRegistration<SegmentsCriteriaContributor>
		_serviceRegistration;

}