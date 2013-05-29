<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<form>
	<fieldset>
		<legend>Add Page</legend>

		<!-- <span class="span9"> -->
		<span class="span8">
			<aui:input name="" placeholder="name" type="text" />
		</span>

		<span class="span3">
			<aui:input name="hidden" label="hidden" type="checkbox" />
		</span>

		<span class="span12">
			<liferay-ui:panel-container cssClass="message-boards-panels" extended="<%= false %>" id="messageBoardsPanelContainer" persistState="<%= true %>">

				<liferay-ui:panel collapsible="<%= true %>" cssClass="threads-panel" extended="<%= true %>" id="chooseTemplatePanel" persistState="<%= true %>" title="templates">
					<liferay-util:include page="/html/portlet/dockbar/search_templates.jsp" />

					<aui:nav cssClass="nav-list no-margin-nav-list">
						<div>
							<h5>Blank (default)</h5>
							Donec sit amet enim mi, sit amet blandit est. Sed id sapien auctor.
							</br>
							<a href="#">
								<liferay-ui:message key="choose-page-layout" />
							</a>
						</div>

						<div>
							<h5>Blog</h5>
							Vivamus nec pulvinar lectus. Donec condimentum, augue id congue porttitor, libero enim semper.
							</br>
						</div>

						<div>
							<h5>Wiki</h5>
							Etiam ut dui ut sem consequat viverra sed eget magna. Phasellus vulputate, lacus.
							</br>
						</div>

						<div>
							<h5>Content Page Display</h5>
							In lorem sapien, pellentesque ac faucibus sit amet, elementum a eros. Donec tempus.
							</br>
						</div>

						<div>
							<h5>Portlet</h5>
							Sed in sem tellus. Duis id ligula orci, sed viverra odio. Quisque mi.
							</br>
						</div>

						<div>
							<h5>Panel</h5>
							Nulla euismod congue imperdiet. Aenean id mi nibh. Pellentesque habitant morbi tristique senectus.
							</br>
						</div>

						<div>
							<h5>Embedded</h5>
							Nullam ultrices commodo purus, in iaculis elit mattis a. Donec in nibh sapien.
							</br>
						</div>

						<div>
							<h5>URL</h5>
							In vel odio vitae sem ullamcorper egestas. Suspendisse potenti. Morbi ut lorem ligula.
							</br>
						</div>

						<div>
							<h5>Link to Page</h5>
							Sed eleifend semper arcu non viverra. Integer tristique pellentesque massa vel venenatis. Sed.
							</br>
						</div>
					</aui:nav>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
			<button type="submit" class="btn btn-primary btn-submit">Save</button>
		</span>
	</fieldset>
</form>