<div class="alert alert-danger publish-error">
	<h4 class="upload-error-message">
		<#if exported && !validated>
			<@liferay.language key="the-publication-process-did-not-start-due-to-validation-errors" />
		<#else>
			<@liferay.language key="an-unexpected-error-occurred-with-the-publication-process.-please-check-your-portal-and-publishing-configuration" />
		</#if>
	</h4>

	<span class="error-message">${htmlUtil.escape(statusMessageJSONObject.getString("message"))}</span>

	<#assign messageListItemsJSONArray = statusMessageJSONObject.getJSONArray("messageListItems")! />

	<#if (messageListItemsJSONArray.iterator())?has_content>
		<ul class="error-list-items">
			<#list messageListItemsJSONArray.iterator() as messageListItemJSONObject>
				<#assign
					info = messageListItemJSONObject.getString("info")
					name = messageListItemJSONObject.getString("name")
				/>

				<li>
					${messageListItemJSONObject.getString("type")}

					${messageListItemJSONObject.getString("site")}:

					<strong>${htmlUtil.escape(name)}</strong>

					<#if info??>
						<span class="error-info">(${htmlUtil.escape(info)})</span>
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</div>

<#assign warningMessagesJSONArray = statusMessageJSONObject.getJSONArray("warningMessages")! />

<#if (warningMessagesJSONArray.iterator())?has_content>
	<div class="alert upload-error">
		<span class="error-message">
			<#if (messageListItemsJSONArray.iterator())?has_content>
				<@liferay.language key="consider-that-the-following-data-would-not-have-been-published-either" />
			<#else>
				<@liferay.language key="the-following-data-has-not-been-published" />
			</#if>
		</span>

		<ul class="error-list-items">
			<#list warningMessagesJSONArray.iterator() as warningMessageJSONObject>
				<#assign
					info = warningMessageJSONObject.getString("info")
					name = warningMessageJSONObject.getString("name")
				/>

				<li>
					${warningMessageJSONObject.getString("type")}

					<strong>${warningMessageJSONObject.getString("size")}</strong>

					<#if info??>
						<span class="error-info">(${htmlUtil.escape(info)})</span>
					</#if>
				</li>
			</#list>
		</ul>
	</div>
</#if>