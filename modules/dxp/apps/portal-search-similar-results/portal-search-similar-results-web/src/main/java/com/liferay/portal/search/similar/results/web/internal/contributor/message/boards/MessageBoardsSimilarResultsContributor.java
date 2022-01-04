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

package com.liferay.portal.search.similar.results.web.internal.contributor.message.boards;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.similar.results.web.internal.builder.AssetTypeUtil;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class MessageBoardsSimilarResultsContributor
	implements SimilarResultsContributor {

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String[] parameters = _httpHelper.getFriendlyURLParameters(
			routeHelper.getURLString());

		SearchStringUtil.requireEquals("message_boards", parameters[0]);

		_putAttribute(parameters[1], "type", routeBuilder);
		_putAttribute(Long.valueOf(parameters[2]), "id", routeBuilder);
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder inputBuilder, CriteriaHelper inputHelper) {

		String type = (String)inputHelper.getRouteParameter("type");
		Long id = (Long)inputHelper.getRouteParameter("id");

		List<?> list = _getMBMessageData(type, id, inputHelper.getGroupId());

		if (list == null) {
			list = _getMBCategoryData(type, id);
		}

		if (list == null) {
			return;
		}

		String className = (String)list.get(0);
		Long classPK = (Long)list.get(1);

		inputBuilder.type(
			className
		).uid(
			Field.getUID(className, String.valueOf(classPK))
		);
	}

	@Reference(unbind = "-")
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setHttpHelper(HttpHelper httpHelper) {
		_httpHelper = httpHelper;
	}

	@Reference(unbind = "-")
	public void setMbCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	@Reference(unbind = "-")
	public void setMbMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		String type = (String)destinationHelper.getRouteParameter("type");
		Long id = (Long)destinationHelper.getRouteParameter("id");

		destinationBuilder.replace(
			type,
			AssetTypeUtil.getAssetTypeByClassName(
				destinationHelper.getClassName())
		).replace(
			String.valueOf(id), String.valueOf(destinationHelper.getClassPK())
		);
	}

	protected static final String CATEGORY = "category";

	protected static final String MESSAGE = "message";

	private List<?> _getMBCategoryData(String type, long id) {
		if (!CATEGORY.equals(type)) {
			return null;
		}

		MBCategory mbCategory = _mbCategoryLocalService.fetchMBCategory(id);

		if (mbCategory != null) {
			return Arrays.asList(
				MBCategory.class.getName(), mbCategory.getCategoryId());
		}

		return null;
	}

	private List<?> _getMBMessageData(String type, long id, long groupId) {
		if (!MESSAGE.equals(type)) {
			return null;
		}

		MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(id);

		if (mbMessage != null) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				groupId, mbMessage.getUuid());

			if (assetEntry != null) {
				return Arrays.asList(
					assetEntry.getClassName(), mbMessage.getRootMessageId());
			}
		}

		return null;
	}

	private void _putAttribute(
		Object value, String name, RouteBuilder routeBuilder) {

		routeBuilder.addAttribute(name, value);
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private HttpHelper _httpHelper;
	private MBCategoryLocalService _mbCategoryLocalService;
	private MBMessageLocalService _mbMessageLocalService;

}