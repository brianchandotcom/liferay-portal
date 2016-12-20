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
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-padding" type="toggle-switch" />

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
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="lfr-use-for-all-margin" type="toggle-switch" />

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