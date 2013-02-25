<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />

<#if entries?has_content>
	<style>
		#<@liferay_portlet.namespace />Carousel .aui-carousel-item {
			height: 250px;
			width: 700px;
		}
	</style>

	<div id="<@liferay_portlet.namespace />Carousel">
		<#assign imageMimeTypes = propsUtil.getArray("dl.file.entry.preview.image.mime.types") />

		<#list entries as entry>
			<#if imageMimeTypes?seq_contains(entry.getMimeType()) >
				<div class="aui-carousel-item" style="background: url(${dlUtil.getPreviewURL(entry, entry.getFileVersion(), themeDisplay, "")}) no-repeat;" ></div>
			</#if>
		</#list>
	</div>

	<@aui.script use="aui-carousel">
		new A.Carousel(
			{
				contentBox: '#<@liferay_portlet.namespace />Carousel',
				height: 250,
				intervalTime: 2,
				width: 700
			}
		).render();
	</@aui.script>
</#if>