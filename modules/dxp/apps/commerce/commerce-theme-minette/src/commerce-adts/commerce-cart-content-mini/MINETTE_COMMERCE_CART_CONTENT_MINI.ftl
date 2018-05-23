<#if entries?has_content>
	<ul class="commerce-order-items-header list-unstyled">
		<li class="autofit-row">
			<div class="autofit-col autofit-col-expand">
				<h4 class="commerce-title">Items (#)</h4>
			</div>

			<div class="autofit-col">
				<div>
					<a class="btn btn-primary" href="${commerceCartContentMiniDisplayContext.getCommerceCartPortletURL()}">
						Edit Cart
					</a>
				</div>
			</div>
		</li>
	</ul>

	<div class="commerce-order-items-body" id="<@portlet.namespace />entriesContainer">
		${commerceCartContentMiniDisplayContext}
	</div>
</#if>