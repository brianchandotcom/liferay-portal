<liferay-portlet:renderURL varImpl="viewProductsURL">
	<portlet:param name="toolbarItem" value="view-all-product-definitions" />
	<portlet:param name="jspPage" value="/commerce_product_definitions/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewProductsURL.toString() %>"
			label="Catalog"
			selected='<%= toolbarItem.equals("view-all-product-definitions") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>