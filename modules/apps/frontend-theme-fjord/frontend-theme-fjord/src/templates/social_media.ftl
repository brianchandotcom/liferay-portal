<#if facebook_icon || twitter_icon>
	<aside id="social-networks">
		<ul class="list-inline">
			<#if facebook_icon>
				<li class="list-inline-item">
					<a href="${facebook_icon_link_url}" rel="external" target="_blank" title="Go to our Facebook (in new window)">
						<span class="icon-facebook icon-monospaced"></span>
					</a>
				</li>
			</#if>

			<#if twitter_icon>
				<li class="list-inline-item">
					<a href="${twitter_icon_link_url}" rel="external" target="_blank" title="Go to our Twitter (in new window)">
						<span class="icon-monospaced icon-twitter"></span>
					</a>
				</li>
			</#if>
		</ul>
	</aside>
</#if>