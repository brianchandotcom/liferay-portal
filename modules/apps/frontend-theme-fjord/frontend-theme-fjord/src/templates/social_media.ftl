<#if facebook_icon || twitter_icon>
	<ul class="list-inline mb-0">
		<#if facebook_icon>
			<li class="list-inline-item">
				<a class="bg-white bg-white-10 d-block fjord-social fjord-text-primary rounded-circle text-center text-decoration-none text-primary" href="${facebook_icon_link_url}" rel="external" target="_blank" title="Go to our Facebook (in new window)">
					<span class="icon-facebook icon-monospaced"></span>
				</a>
			</li>
		</#if>

		<#if twitter_icon>
			<li class="list-inline-item">
				<a class="bg-white bg-white-10 d-block fjord-social fjord-text-info rounded-circle text-center text-decoration-none text-info" href="${twitter_icon_link_url}" rel="external" target="_blank" title="Go to our Twitter (in new window)">
					<span class="icon-monospaced icon-twitter"></span>
				</a>
			</li>
		</#if>
	</ul>
</#if>