<#if socialMediaFacebook || socialMediaTwitter>
	<aside id="social-networks">
		<ul class="list-inline">
			<#if socialMediaFacebook>
				<li>
					<a href="${socialMediaFacebookUrl}" rel="external" target="_blank" title="Go to our Facebook (in new window)">
						<span class="icon-facebook icon-monospaced"></span>
					</a>
				</li>
			</#if>

			<#if socialMediaTwitter>
				<li>
					<a href="${socialMediaTwitterUrl}" rel="external" target="_blank" title="Go to our Twitter (in new window)">
						<span class="icon-monospaced icon-twitter"></span>
					</a>
				</li>
			</#if>
		</ul>
	</aside>
</#if>