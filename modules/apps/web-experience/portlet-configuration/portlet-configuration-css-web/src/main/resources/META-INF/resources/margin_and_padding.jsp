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
JSONObject spacingDataJSONObject = portletSetupJSONObject.getJSONObject("spacingData");

JSONObject marginSONObject = spacingDataJSONObject.getJSONObject("margin");
JSONObject paddingJSONObject = spacingDataJSONObject.getJSONObject("padding");
%>

<aui:row>
	<aui:col cssClass="lfr-padding use-for-all-column" width="<%= 50 %>">
		<aui:fieldset label="padding">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllPadding" type="toggle-switch" value='<%= paddingJSONObject.getBoolean("sameForAll") %>' />

			<%
			JSONObject paddingTopJSONObject = paddingJSONObject.getJSONObject("top");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="paddingTop" value='<%= paddingTopJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="paddingTopUnit" title="top-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(paddingTopJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(paddingTopJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(paddingTopJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject paddingRightJSONObject = paddingJSONObject.getJSONObject("right");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="paddingRight" value='<%= paddingRightJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="paddingRightUnit" title="right-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(paddingRightJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(paddingRightJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(paddingRightJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject paddingBottomJSONObject = paddingJSONObject.getJSONObject("bottom");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="paddingBottom" value='<%= paddingBottomJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="paddingBottomUnit" title="bottom-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(paddingBottomJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(paddingBottomJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(paddingBottomJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject paddingLeftJSONObject = paddingJSONObject.getJSONObject("left");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="paddingLeft" value='<%= paddingLeftJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="paddingLeftUnit" title="left-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(paddingLeftJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(paddingLeftJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(paddingLeftJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-margin use-for-all-column" last="<%= true %>" width="<%= 50 %>">
		<aui:fieldset label="margin">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllMargin" type="toggle-switch" value='<%= marginSONObject.getBoolean("sameForAll") %>' />

			<%
			JSONObject marginTopJSONObject = marginSONObject.getJSONObject("top");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="marginTop" value='<%= marginTopJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="marginTopUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(marginTopJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(marginTopJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(marginTopJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject marginRightJSONObject = marginSONObject.getJSONObject("right");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="marginRight" value='<%= marginRightJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="marginRightUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(marginRightJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(marginRightJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(marginRightJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject marginBottomJSONObject = marginSONObject.getJSONObject("bottom");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="marginBottom" value='<%= marginBottomJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="marginBottomUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(marginBottomJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(marginBottomJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(marginBottomJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject marginLeftJSONObject = marginSONObject.getJSONObject("left");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="marginLeft" value='<%= marginLeftJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="marginLeftUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(marginLeftJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(marginLeftJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(marginLeftJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>
</aui:row>