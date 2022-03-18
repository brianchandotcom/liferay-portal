<div class="form-group-autofit">
	<div class="form-group-item form-group-item-label form-group-item-shrink">
		<label>
			<span class="text-truncate-inline">
				<span class="text-truncate">
					${languageUtil.get(locale, "sort-by")}
				</span>
			</span>
		</label>
	</div>

	<div class="form-group-item">
		<@liferay_aui.select
			cssClass="sort-term"
			label=""
			name="sortSelection"
		>
			<#if entries?has_content>
				<#list entries as entry>
					<@liferay_aui.option
						label="${entry.getLanguageLabel()}"
						selected=entry.isSelected()
						value="${entry.getField()}"
					/>
				</#list>
			</#if>
		</@liferay_aui.select>
	</div>
</div>