<div style="background-color: #f7f8f9; color: #6b6c7e; font-family: system-ui, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen-Sans, Ubuntu, Cantarell, Helvetica Neue, Arial, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol; font-size: 20px; height: 100%; padding: 24px;">
	<div style="background-color: white; border: solid 1px #e7e7ed; border-radius: 4px; margin: 0 auto; max-width: 800px; padding: 40px;">
		<p style="line-height: 1.5; margin: 0;">
			${content}
		</p>

		<#if sharingEntryURL?has_content>
			<a href="${sharingEntryURL}" style="background-color: #145ffb; color: white; border-radius: 4px; box-sizing: border-box; display: inline-block; font-size: 16px; height: 40px; line-height: 24px; margin-top: 24px; padding: 7px 15px; text-decoration: none;">${actionTitle}</a>
		</#if>
	</div>
</div>