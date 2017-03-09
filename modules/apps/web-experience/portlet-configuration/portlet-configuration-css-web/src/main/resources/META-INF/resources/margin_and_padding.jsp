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
	<aui:col cssClass="lfr-padding use-for-all-column" width="<%= 50 %>">
		<aui:fieldset label="padding">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllPadding" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="paddingTop" />

				<aui:select inlineField="<%= true %>" label="" name="paddingTopUnit" title="top-padding-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="paddingRight" />

				<aui:select inlineField="<%= true %>" label="" name="paddingRightUnit" title="right-padding-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="paddingBottom" />

				<aui:select inlineField="<%= true %>" label="" name="paddingBottomUnit" title="bottom-padding-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="paddingLeft" />

				<aui:select inlineField="<%= true %>" label="" name="paddingLeftUnit" title="left-padding-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-margin use-for-all-column" last="<%= true %>" width="<%= 50 %>">
		<aui:fieldset label="margin">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllMargin" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="marginTop" />

				<aui:select inlineField="<%= true %>" label="" name="marginTopUnit" title="top-margin-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="marginRight" />

				<aui:select inlineField="<%= true %>" label="" name="marginRightUnit" title="top-margin-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="marginBottom" />

				<aui:select inlineField="<%= true %>" label="" name="marginBottomUnit" title="top-margin-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="marginLeft" />

				<aui:select inlineField="<%= true %>" label="" name="marginLeftUnit" title="top-margin-unit">
					<aui:option label="%" />
					<aui:option label="px" />
					<aui:option label="em" />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>
</aui:row>