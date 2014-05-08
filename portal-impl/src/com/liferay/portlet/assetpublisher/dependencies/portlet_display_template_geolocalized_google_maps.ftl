<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />
<#assign liferay_aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<#assign group = themeDisplay.getScopeGroup() />

<#assign apiKey = group.getLiveParentTypeSettingsProperty("googleMapsKey")!"" />

<#assign defaultLatitude = -3.6833 />
<#assign defaultLongitude = 40.40 />

<#if apiKey = "">
	<#assign companyPrefs = prefsPropsUtil.getPreferences(companyId) />

	<#assign apiKey = companyPrefs.getValue("googleMapsKey", "") />
</#if>

<#if apiKey = "">
	<div class="alert alert-warning">
		${languageUtil.get(locale, "please-configure-your-google-maps-key-in-the-site-or-portal-settings")}
	</div>
<#else>
	<#assign minHeight = "400px" />
	<#assign minWidth = "400px" />

	<#assign namespace = renderResponse.getNamespace() />

	<#assign showEditURL = paramUtil.getBoolean(renderRequest, "showEditURL", true) />

	<#assign images = {
		"com.liferay.portlet.documentlibrary.model.DLFileEntry": "http://maps.google.com/mapfiles/ms/icons/green-dot.png",
		"com.liferay.portlet.dynamicdatalists.model.DDLRecord": "http://maps.google.com/mapfiles/ms/icons/red-dot.png",
		"com.liferay.portlet.journal.model.JournalArticle": "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
		"default": "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
	} />

	<#assign jsonArray = jsonFactoryUtil.createJSONArray() />

	<#list entries as entry>
		<#assign assetRenderer = entry.getAssetRenderer() />

		<#assign ddmReader = assetRenderer.getDDMFieldReader() />

		<#assign fields = ddmReader.getFields("geolocation") />

		<#list fields.iterator() as field>
			<#assign jsonObject = jsonFactoryUtil.createJSONObject(field.getValue()) />

			<@liferay.silently jsonObject.put("title", assetRenderer.getTitle(locale)) />

			<#assign entryAbstract>
				<@getAbstract asset = entry />
			</#assign>

			<@liferay.silently jsonObject.put("abstract", entryAbstract) />

			<#if images?keys?seq_contains(entry.getClassName())>
				<@liferay.silently jsonObject.put("icon", images[entry.getClassName()]) />
			<#else>
				<@liferay.silently jsonObject.put("icon", images["default"]) />
			</#if>

			<@liferay.silently jsonArray.put(jsonObject) />
		</#list>
	</#list>

	<style type="text/css">
		.asset-entry-abstract {
			min-width: ${minWidth};
		}

		.asset-entry-abstract .asset-entry-abstract-image {
			float: left;
		}

		.asset-entry-abstract .asset-entry-abstract-image img {
			display: block;
		}

		.asset-entry-abstract .asset-entry-abstract-content {
			margin-left: 10em;
		}

		.asset-entry-abstract .taglib-icon {
			float: right;
		}

		.map-canvas {
			min-height: ${minHeight};
		}

		.gmnoprint img {
			max-width: none;
		}
	</style>

	<div id="${namespace}map-canvas" class="map-canvas"></div>

	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=${apiKey}&sensor=true"></script>

	<@liferay_aui.script>
		(function () {
			function putMarkers(map) {
				var points = ${jsonArray};

				var len = points.length;

				if (len == 0) {
					return null;
				}

				var bounds = new google.maps.LatLngBounds();

				for (var i = 0; i < len; i++) {
					var point = points[i];

					var marker = new google.maps.Marker(
						{
							icon: point["icon"],
							map: map,
							position: new google.maps.LatLng(point["latitude"], point["longitude"]),
							title: point["title"]
						}
					);

					bounds.extend(marker.position);

					(function (marker) {
						var infoWindow = new google.maps.InfoWindow(
							{
								content: point["abstract"] || point["title"]
							});

						google.maps.event.addListener(
							marker, 'click',
							function () {
								infoWindow.open(map, marker);
							});
					})(marker);
				}

				return bounds;
			}

			function drawMap(mapOptions) {
				var map = new google.maps.Map(document.getElementById("${namespace}map-canvas"), mapOptions);

				var bounds = putMarkers(map);

				if (bounds) {
					map.fitBounds(bounds);
					map.panToBounds(bounds);
				}
			}

			if ("geolocation" in navigator) {
				navigator.geolocation.getCurrentPosition(
					function (pos) {
						drawMap(
							{
								center: new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude),
								zoom: 8
							});
					});
			}
			else {
				drawMap({
					center: new google.maps.LatLng(${defaultLatitude}, ${defaultLongitude}),
					zoom: 8
				});
			}
		})();
	</@liferay_aui.script>

	<#macro getAbstract asset>
		<#assign assetRenderer = asset.getAssetRenderer() />

		<#assign redirectURL = renderResponse.createLiferayPortletURL(themeDisplay.getPlid(), themeDisplay.getPortletDisplay().getId(), "RENDER_PHASE", false) />

		${redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}

		<div class="asset-entry-abstract">
			<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("POP_UP"), redirectURL) />

			<#assign taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + htmlUtil.escapeJS(languageUtil.format(locale, "edit-x", htmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + htmlUtil.escapeJS(editPortletURL.toString()) + "'});" />

			<#if showEditURL && assetRenderer.hasEditPermission(permissionChecker)>
				<@liferay_ui.icon
					image = "edit"
					label = true
					message = "edit"
					url = taglibEditURL
				/>
			</#if>

			<div class="asset-entry-abstract-image">
				<img src="${assetRenderer.getThumbnailPath(renderRequest)}" />
			</div>

			<#assign assetURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, asset) />

			<div class="asset-entry-abstract-content">
				<h3><a href="${assetURL}">${assetRenderer.getTitle(locale)}</a></h3>

				<div>
					${assetRenderer.getSummary(renderRequest, renderResponse)}
				</div>
			</div>

			<div class="asset-entry-abstract-footer">
				<a href="${assetURL}">${languageUtil.get(locale, "read-more")} &raquo;</a>
			</div>
		</div>
	</#macro>
</#if>