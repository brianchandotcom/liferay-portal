<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<aui:row>
	<aui:col cssClass="lfr-border-width use-for-all-column" width="<%= 33 %>">
		<aui:fieldset label="border-width">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' data-inputselector=".same-border-width" label="same-for-all" name="useForAllWidth" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="borderWidthTop" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthTopUnit" title="top-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="right" name="borderWidthRight" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthRightUnit" title="right-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="bottom" name="borderWidthBottom" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthBottomUnit" title="bottom-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="left" name="borderWidthLeft" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthLeftUnit" title="left-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' data-inputselector=".same-border-style" label="same-for-all" name="useForAllStyle" type="toggle-switch" />

			<aui:select label="top" name="borderStyleTop" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="right" name="borderStyleRight" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="bottom" name="borderStyleBottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="left" name="borderStyleLeft" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "solid") %>' />
			</aui:select>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-color" last="<%= true %>" width="<%= 33 %>">
		<aui:fieldset label="border-color">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>' data-inputselector=".same-border-color" label="same-for-all" name="useForAllColor" type="toggle-switch" />

			<div>

				<%
				String colorTop = portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderColor");
				String labelTop = LanguageUtil.get(request, "top");
				String nameTop = renderResponse.getNamespace() + "borderColorTop";

				Map<String, Object> dataBorderTop = new HashMap<>();

				dataBorderTop.put("color", colorTop);
				dataBorderTop.put("label", labelTop);
				dataBorderTop.put("name", nameTop);
				%>

				<div class="form-group">
					<input name="<%= nameTop %>" type="hidden" value="#<%= colorTop %>" />

					<div class="clay-color-picker">
						<label><%= labelTop %></label>

						<div class="clay-color input-group">
							<div class="input-group-item input-group-item-shrink input-group-prepend">
								<div class="input-group-text">
									<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= colorTop %>" type="button" />
								</div>
							</div>

							<div class="input-group-append input-group-item">
								<input class="form-control input-group-inset input-group-inset-before" type="hidden" />
								<label class="input-group-inset-item input-group-inset-item-before"><%= colorTop %></label>
							</div>
						</div>
					</div>
				</div>

				<react:component
					data="<%= dataBorderTop %>"
					module="js/ColorPickerInput.es"
					servletContext="<%= application %>"
				/>
			</div>

			<fieldset class="same-border-color" <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") ? "disabled" : StringPool.BLANK %>>
				<div>

					<%
					String colorRight = portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderColor");
					String labelRight = LanguageUtil.get(request, "right");
					String nameRight = renderResponse.getNamespace() + "borderColorRight";

					Map<String, Object> dataBorderRight = new HashMap<>();

					dataBorderRight.put("color", colorRight);
					dataBorderRight.put("label", labelRight);
					dataBorderRight.put("name", nameRight);
					%>

					<div class="form-group">
						<input name="<%= nameRight %>" type="hidden" value="#<%= colorRight %>" />

						<div class="clay-color-picker">
							<label><%= labelRight %></label>

							<div class="clay-color input-group">
								<div class="input-group-item input-group-item-shrink input-group-prepend">
									<div class="input-group-text">
										<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= colorRight %>" type="button" />
									</div>
								</div>

								<div class="input-group-append input-group-item">
									<input class="form-control input-group-inset input-group-inset-before" type="hidden" />
									<label class="input-group-inset-item input-group-inset-item-before"><%= colorRight %></label>
								</div>
							</div>
						</div>
					</div>

					<react:component
						data="<%= dataBorderRight %>"
						module="js/ColorPickerInput.es"
						servletContext="<%= application %>"
					/>
				</div>

				<div>

					<%
					String colorBottom = portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderColor");
					String labelBottom = LanguageUtil.get(request, "bottom");
					String nameBottom = renderResponse.getNamespace() + "borderColorBottom";

					Map<String, Object> dataBorderBottom = new HashMap<>();

					dataBorderBottom.put("color", colorBottom);
					dataBorderBottom.put("label", labelBottom);
					dataBorderBottom.put("name", nameBottom);
					%>

					<div class="form-group">
						<input name="<%= nameBottom %>" type="hidden" value="#<%= colorBottom %>" />

						<div class="clay-color-picker">
							<label><%= labelBottom %></label>

							<div class="clay-color input-group">
								<div class="input-group-item input-group-item-shrink input-group-prepend">
									<div class="input-group-text">
										<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= colorBottom %>" type="button" />
									</div>
								</div>

								<div class="input-group-append input-group-item">
									<input class="form-control input-group-inset input-group-inset-before" type="hidden" />
									<label class="input-group-inset-item input-group-inset-item-before"><%= colorBottom %></label>
								</div>
							</div>
						</div>
					</div>

					<react:component
						data="<%= dataBorderBottom %>"
						module="js/ColorPickerInput.es"
						servletContext="<%= application %>"
					/>
				</div>

				<div>

					<%
					String colorLeft = portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderColor");
					String labelLeft = LanguageUtil.get(request, "left");
					String nameLeft = renderResponse.getNamespace() + "borderColorLeft";

					Map<String, Object> dataBorderLeft = new HashMap<>();

					dataBorderLeft.put("color", colorLeft);
					dataBorderLeft.put("label", labelLeft);
					dataBorderLeft.put("name", nameLeft);
					%>

					<div class="form-group">
						<input name="<%= nameLeft %>" type="hidden" value="#<%= colorLeft %>" />

						<div class="clay-color-picker">
							<label><%= labelLeft %></label>

							<div class="clay-color input-group">
								<div class="input-group-item input-group-item-shrink input-group-prepend">
									<div class="input-group-text">
										<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= colorLeft %>" type="button" />
									</div>
								</div>

								<div class="input-group-append input-group-item">
									<input class="form-control input-group-inset input-group-inset-before" type="hidden" />
									<label class="input-group-inset-item input-group-inset-item-before"><%= colorLeft %></label>
								</div>
							</div>
						</div>
					</div>

					<react:component
						data="<%= dataBorderLeft %>"
						module="js/ColorPickerInput.es"
						servletContext="<%= application %>"
					/>
				</div>
			</fieldset>
		</aui:fieldset>
	</aui:col>
</aui:row>