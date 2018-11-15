<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%">
	<tbody>
		<tr>
			<td style="color: #555; display: inline-block; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 1.3em; font-weight: 200; line-height: 1.5em; padding-bottom: 1em; padding-top: 1em;">
				<#if fromUserPortraitURL?has_content>
					<img src="${fromUserPortraitURL}" style="border-radius: 1.5em; display: inline-block; margin-right: 0.1em; max-height: 1.5em; overflow: hidden; vertical-align: middle; width: 1.5em;" />
				</#if>

				${content}
			</td>
		</tr>
		<#if sharingEntryURL?has_content>
			<tr>
				<td align="left" style="padding-top: 1em;" valign="middle">
					<table border="0" cellpadding="0" cellspacing="0" style="background-color: #8D8D8D; border-collapse: separate; border-radius: 4px;">
						<tbody>
							<tr>
								<td align="center" style="height: 34px; line-height: 100%;" valign="middle">
									<a href="${sharingEntryURL}" style="color: #FFF; display: block; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-weight: 200; padding-bottom: 10px; padding-left: 10px; padding-right:10px; padding-top: 10px; text-decoration: none; vertical-align: text-bottom;">
										${actionTitle}
									</a>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</#if>
	</tbody>
</table>