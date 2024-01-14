/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.RowViewport;
import com.liferay.headless.delivery.dto.v1_0.RowViewportDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class RowLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

	public RowLayoutStructureItemMapper(
		InfoItemServiceRegistry infoItemServiceRegistry, Portal portal) {

		super(infoItemServiceRegistry, portal);
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				setDefinition(
					() -> new PageRowDefinition() {
						{
							setCssClasses(
								() ->
									StyledLayoutStructureItemUtil.getCssClasses(
										rowStyledLayoutStructureItem));
							setCustomCSS(
								() ->
									StyledLayoutStructureItemUtil.getCustomCSS(
										rowStyledLayoutStructureItem));
							setCustomCSSViewports(
								() ->
									StyledLayoutStructureItemUtil.
										getCustomCSSViewports(
											rowStyledLayoutStructureItem));
							setFragmentStyle(
								() -> {
									JSONObject itemConfigJSONObject =
										rowStyledLayoutStructureItem.
											getItemConfigJSONObject();

									return toFragmentStyle(
										itemConfigJSONObject.getJSONObject(
											"styles"),
										saveMappingConfiguration);
								});
							setFragmentViewports(
								() -> getFragmentViewPorts(
									rowStyledLayoutStructureItem.
										getItemConfigJSONObject()));
							setGutters(rowStyledLayoutStructureItem::isGutters);
							setIndexed(rowStyledLayoutStructureItem::isIndexed);
							setModulesPerRow(
								() ->
									rowStyledLayoutStructureItem.
										getModulesPerRow());
							setName(rowStyledLayoutStructureItem::getName);
							setNumberOfColumns(
								() ->
									rowStyledLayoutStructureItem.
										getNumberOfColumns());
							setReverseOrder(
								() ->
									rowStyledLayoutStructureItem.
										isReverseOrder());
							setRowViewports(
								() -> {
									Map<String, JSONObject>
										rowViewportConfigurationJSONObjects =
											rowStyledLayoutStructureItem.
												getViewportConfigurationJSONObjects();

									if (MapUtil.isEmpty(
											rowViewportConfigurationJSONObjects)) {

										return null;
									}

									List<RowViewport> rowViewports =
										new ArrayList<RowViewport>() {
											{
												add(
													_toRowViewport(
														rowViewportConfigurationJSONObjects,
														ViewportSize.
															MOBILE_LANDSCAPE));
												add(
													_toRowViewport(
														rowViewportConfigurationJSONObjects,
														ViewportSize.
															PORTRAIT_MOBILE));
												add(
													_toRowViewport(
														rowViewportConfigurationJSONObjects,
														ViewportSize.TABLET));
											}
										};

									return rowViewports.toArray(
										new RowViewport[0]);
								});
							setVerticalAlignment(
								() ->
									rowStyledLayoutStructureItem.
										getVerticalAlignment());
						}
					});
				setId(layoutStructureItem::getItemId);
				setType(() -> Type.ROW);
			}
		};
	}

	private RowViewport _toRowViewport(
		Map<String, JSONObject> rowViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		return new RowViewport() {
			{
				setId(viewportSize::getViewportSizeId);
				setRowViewportDefinition(
					() -> _toRowViewportDefinition(
						rowViewportConfigurationJSONObjects, viewportSize));
			}
		};
	}

	private RowViewportDefinition _toRowViewportDefinition(
		Map<String, JSONObject> rowViewportConfigurationJSONObjects,
		ViewportSize viewportSize) {

		if (!rowViewportConfigurationJSONObjects.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = rowViewportConfigurationJSONObjects.get(
			viewportSize.getViewportSizeId());

		return new RowViewportDefinition() {
			{
				setModulesPerRow(
					() -> {
						if (!jsonObject.has("modulesPerRow")) {
							return null;
						}

						return jsonObject.getInt("modulesPerRow");
					});
				setReverseOrder(
					() -> {
						if (!jsonObject.has("reverseOrder")) {
							return null;
						}

						return jsonObject.getBoolean("reverseOrder");
					});
				setVerticalAlignment(
					() -> {
						if (!jsonObject.has("verticalAlignment")) {
							return null;
						}

						return jsonObject.getString("verticalAlignment");
					});
			}
		};
	}

}