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

<%
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(portletResource) && PortletPermissionUtil.contains(permissionChecker, themeDisplay.getLayout(), portletResource, ActionKeys.CONFIGURATION) %>">

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		String tabs1Names = "portlet-configuration,text-styles,background-styles,border-styles,margin-and-padding,advanced-styling";

		if (PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED) {
			tabs1Names = tabs1Names + ",wap-styling";
		}

		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

		decimalFormatSymbols.setDecimalSeparator('.');
		%>

		<div id="lfr-look-and-feel">
			<div class="tabbable-content" id="portlet-set-properties">
				<liferay-ui:tabs
					names="<%= tabs1Names %>"
					refresh="<%= false %>"
					url="<%= portletURL.toString() %>"
				/>

				<aui:form method="post">
					<input id="portlet-area" name="portlet-area" type="hidden" />
					<input id="portlet-boundary-id" name="portlet-boundary-id" type="hidden" />

					<div class="tab-pane">
						<aui:fieldset id="portlet-config">
							<aui:input name="use-custom-title" type="checkbox" />

							<span class="field-row">
								<aui:input inlineField="<%= true %>" label="" name="custom-title" />

								<aui:select inlineField="<%= true %>" label="" name="lfr-portlet-language" title="language">

									<%
									for (Locale curLocale : LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId())) {
									%>

										<aui:option label="<%= curLocale.getDisplayName(locale) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

									<%
									}
									%>

								</aui:select>
							</span>

							<aui:select label="link-portlet-urls-to-page" name="lfr-point-links">
								<aui:option label="current-page" value="" />

								<%
								String linkToLayoutUuid = StringPool.BLANK;

								Group group = layout.getGroup();

								List<LayoutDescription> layoutDescriptions = LayoutListUtil.getLayoutDescriptions(layout.getGroup().getGroupId(), layout.isPrivateLayout(), group.getGroupKey(), locale);

								for (LayoutDescription layoutDescription : layoutDescriptions) {
									Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

									if (layoutDescriptionLayout != null) {
								%>

										<aui:option label="<%= layoutDescription.getDisplayName() %>" selected="<%= layoutDescriptionLayout.getUuid().equals(linkToLayoutUuid) %>" value="<%= layoutDescriptionLayout.getUuid() %>" />

								<%
									}
								}
								%>

							</aui:select>

							<aui:select label="show-borders" name="show-borders" showEmptyOption="<%= true %>">
								<aui:option label="yes" value="true" />
								<aui:option label="no" value="false" />
							</aui:select>

							<span class="alert alert-info form-hint hide" id="border-note">
								<liferay-ui:message key="this-change-will-only-be-shown-after-you-refresh-the-page" />
							</span>
						</aui:fieldset>

						<aui:fieldset id="text-styles">
							<aui:row>
								<aui:col width="<%= 33 %>">
									<aui:select label="font" name="lfr-font-family" showEmptyOption="<%= true %>">
										<aui:option label="Arial" />
										<aui:option label="Georgia" />
										<aui:option label="Times New Roman" />
										<aui:option label="Tahoma" />
										<aui:option label="Trebuchet MS" />
										<aui:option label="Verdana" />
									</aui:select>

									<aui:input label="bold" name="lfr-font-bold" type="checkbox" />

									<aui:input label="italic" name="lfr-font-italic" type="checkbox" />

									<aui:select label="size" name="lfr-font-size" showEmptyOption="<%= true %>">

										<%
										DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

										for (double i = 0.1; i <= 12; i += 0.1) {
											String value = decimalFormat.format(i);
										%>

											<aui:option label="<%= value %>" />

										<%
										}
										%>

									</aui:select>

									<aui:input label="color" name="lfr-font-color" />

									<aui:select label="alignment" name="lfr-font-align" showEmptyOption="<%= true %>">
										<aui:option label="justify" />
										<aui:option label="left" />
										<aui:option label="right" />
										<aui:option label="center" />
									</aui:select>

									<aui:select label="text-decoration" name="lfr-font-decoration" showEmptyOption="<%= true %>">
										<aui:option label="none" />
										<aui:option label="underline" />
										<aui:option label="overline" />
										<aui:option label="strikethrough" value="line-through" />
									</aui:select>
								</aui:col>

								<aui:col last="<%= true %>" width="<%= 60 %>">
									<aui:select label="word-spacing" name="lfr-font-space" showEmptyOption="<%= true %>">

										<%
										DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

										for (double i = -1; i <= 1; i += 0.05) {
											String value = decimalFormat.format(i);

											if (value.equals("0em")) {
												value = "normal";
											}
										%>

											<aui:option label="<%= value %>" />

										<%
										}
										%>

									</aui:select>

									<aui:select label="line-height" name="lfr-font-leading" showEmptyOption="<%= true %>">

										<%
										DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

										for (double i = 0.1; i <= 12; i += 0.1) {
											String value = decimalFormat.format(i);
										%>

											<aui:option label="<%= value %>" />

										<%
										}
										%>

									</aui:select>

									<aui:select label="letter-spacing" name="lfr-font-tracking" showEmptyOption="<%= true %>">

										<%
											for (int i = -10; i <= 50; i++) {
												String value = i + "px";

												if (i == 0) {
													value = "0";
												}
											%>

												<aui:option label="<%= value %>" />

											<%
											}
										%>

									</aui:select>
								</aui:col>
							</aui:row>
						</aui:fieldset>

						<aui:fieldset id="background-styles">
							<aui:input label="background-color" name="lfr-bg-color" />
						</aui:fieldset>

						<aui:fieldset id="border-styles">
							<aui:row>
								<aui:col cssClass="lfr-border-width use-for-all-column" width="<%= 33 %>">
									<aui:fieldset label="border-width">
										<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-width" type="checkbox" />

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="top" name="lfr-border-width-top" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-top-unit" title="top-border-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="right" name="lfr-border-width-right" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-right-unit" title="right-border-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="bottom" name="lfr-border-width-bottom" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-bottom-unit" title="bottom-border-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="left" name="lfr-border-width-left" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-border-width-left-unit" title="left-border-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>
									</aui:fieldset>
								</aui:col>

								<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
									<aui:fieldset label="border-style">
										<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="lfr-use-for-all-style" type="checkbox" />

										<aui:select label="top" name="lfr-border-style-top" showEmptyOption="<%= true %>">
											<aui:option label="dashed" />
											<aui:option label="double" />
											<aui:option label="dotted" />
											<aui:option label="groove" />
											<aui:option label="hidden[css]" value="hidden" />
											<aui:option label="inset" />
											<aui:option label="outset" />
											<aui:option label="ridge" />
											<aui:option label="solid" />
										</aui:select>

										<aui:select label="right" name="lfr-border-style-right" showEmptyOption="<%= true %>">
											<aui:option label="dashed" />
											<aui:option label="double" />
											<aui:option label="dotted" />
											<aui:option label="groove" />
											<aui:option label="hidden[css]" value="hidden" />
											<aui:option label="inset" />
											<aui:option label="outset" />
											<aui:option label="ridge" />
											<aui:option label="solid" />
										</aui:select>

										<aui:select label="bottom" name="lfr-border-style-bottom" showEmptyOption="<%= true %>">
											<aui:option label="dashed" />
											<aui:option label="double" />
											<aui:option label="dotted" />
											<aui:option label="groove" />
											<aui:option label="hidden[css]" value="hidden" />
											<aui:option label="inset" />
											<aui:option label="outset" />
											<aui:option label="ridge" />
											<aui:option label="solid" />
										</aui:select>

										<aui:select label="left" name="lfr-border-style-left" showEmptyOption="<%= true %>">
											<aui:option label="dashed" />
											<aui:option label="double" />
											<aui:option label="dotted" />
											<aui:option label="groove" />
											<aui:option label="hidden[css]" value="hidden" />
											<aui:option label="inset" />
											<aui:option label="outset" />
											<aui:option label="ridge" />
											<aui:option label="solid" />
										</aui:select>
									</aui:fieldset>
								</aui:col>

								<aui:col cssClass="lfr-border-color" last="<%= true %>" width="<%= 33 %>">
									<aui:fieldset label="border-color">
										<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="lfr-use-for-all-color" type="checkbox" />

										<aui:input label="top" name="lfr-border-color-top" />

										<aui:input label="right" name="lfr-border-color-right" />

										<aui:input label="bottom" name="lfr-border-color-bottom" />

										<aui:input label="left" name="lfr-border-color-left" />
									</aui:fieldset>
								</aui:col>
							</aui:row>
						</aui:fieldset>

						<aui:fieldset cssClass="fieldset spacing" id="spacing-styles">
							<aui:row>
								<aui:col cssClass="lfr-padding use-for-all-column" width="<%= 50 %>">
									<aui:fieldset label="padding">
										<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-padding" type="checkbox" />

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="top" name="lfr-padding-top" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-padding-top-unit" title="top-padding-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="right" name="lfr-padding-right" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-padding-right-unit" title="right-padding-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="bottom" name="lfr-padding-bottom" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-padding-bottom-unit" title="bottom-padding-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="left" name="lfr-padding-left" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-padding-left-unit" title="left-padding-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>
									</aui:fieldset>
								</aui:col>

								<aui:col cssClass="lfr-margin use-for-all-column" last="<%= true %>" width="<%= 50 %>">
									<aui:fieldset label="margin">
										<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-margin" type="checkbox" />

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="top" name="lfr-margin-top" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-margin-top-unit" title="top-margin-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="right" name="lfr-margin-right" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-margin-right-unit" title="top-margin-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="bottom" name="lfr-margin-bottom" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-margin-bottom-unit" title="top-margin-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>

										<span class="field-row">
											<aui:input inlineField="<%= true %>" label="left" name="lfr-margin-left" />

											<aui:select inlineField="<%= true %>" label="" name="lfr-margin-left-unit" title="top-margin-unit">
												<aui:option label="%" />
												<aui:option label="px" />
												<aui:option label="em" />
											</aui:select>
										</span>
									</aui:fieldset>
								</aui:col>
							</aui:row>
						</aui:fieldset>

						<aui:fieldset id="css-styling">
							<aui:input label="enter-your-custom-css-class-names" name="lfr-custom-css-class-name" type="text" />

							<aui:input cssClass="lfr-textarea-container" label="enter-your-custom-css" name="lfr-custom-css" type="textarea" />
						</aui:fieldset>

						<c:if test="<%= PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED %>">
							<aui:fieldset id="wap-styling">
								<aui:input label="title" name="lfr-wap-title" />

								<aui:select label="initial-window-state" name="lfr-wap-initial-window-state">
									<aui:option label="minimized" value="MINIMIZED" />
									<aui:option label="normal" value="NORMAL" />
								</aui:select>
							</aui:fieldset>
						</c:if>

						<aui:button-row>
							<aui:button name="lfr-lookfeel-save" primary="<%= true %>" value="save" />

							<aui:button name="lfr-lookfeel-reset" value="reset" />
						</aui:button-row>
					</div>
				</aui:form>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>