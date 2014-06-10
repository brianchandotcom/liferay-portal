<#assign liferay_aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#assign defaultLatitude = -3.6833 />
<#assign defaultLongitude = 40.40 />

<#assign group = themeDisplay.getScopeGroup() />

<#assign mapsAPIProvider = group.getLiveParentTypeSettingsProperty("mapsAPIProvider")!"" />

<#assign companyPortletPreferences = prefsPropsUtil.getPreferences(companyId) />

<#if mapsAPIProvider = "">
	<#assign mapsAPIProvider = companyPortletPreferences.getValue("mapsAPIProvider", "googleMaps") />
</#if>

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

		<#if mapsAPIProvider = "googleMaps">
			<#assign
				images = {
					"com.liferay.portlet.documentlibrary.model.DLFileEntry": "${themeDisplay.getProtocol()}://maps.google.com/mapfiles/ms/icons/green-dot.png",
					"com.liferay.portlet.dynamicdatalists.model.DDLRecord": "${themeDisplay.getProtocol()}://maps.google.com/mapfiles/ms/icons/red-dot.png",
					"com.liferay.portlet.journal.model.JournalArticle": "${themeDisplay.getProtocol()}://maps.google.com/mapfiles/ms/icons/blue-dot.png",
					"default": "${themeDisplay.getProtocol()}://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
				}
			/>

			<#if images?keys?seq_contains(entry.getClassName())>
				<@liferay.silently jsonObject.put("icon", images[entry.getClassName()]) />
			<#else>
				<@liferay.silently jsonObject.put("icon", images["default"]) />
			</#if>
		</#if>

		<@liferay.silently jsonArray.put(jsonObject) />
	</#list>
</#list>

<div class="map-canvas" id="${renderResponse.getNamespace()}mapCanvas"></div>

<#if mapsAPIProvider = "googleMaps" >
	<style type="text/css">
		#${renderResponse.getNamespace()}assetEntryAbstract {
			min-width: 400px;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .asset-entry-abstract-image {
			float: left;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .asset-entry-abstract-image img {
			display: block;
			margin-right: 2em;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .taglib-icon {
			float: right;
		}

		#${renderResponse.getNamespace()}mapCanvas {
			min-height: 400px;
		}

		#${renderResponse.getNamespace()}mapCanvas img {
			max-width: none;
		}
	</style>

	<#assign apiKey = group.getLiveParentTypeSettingsProperty("googleMapsAPIKey")!"" />

	<#if apiKey = "">
		<#assign apiKey = companyPortletPreferences.getValue("googleMapsAPIKey", "") />
	</#if>

	<#if apiKey = "">
		<script src="${themeDisplay.getProtocol()}://maps.googleapis.com/maps/api/js?sensor=true" type="text/javascript"></script>
	<#else>
		<script src="${themeDisplay.getProtocol()}://maps.googleapis.com/maps/api/js?key=${apiKey}&sensor=true" type="text/javascript"></script>
	</#if>

	<@liferay_aui.script>
		(function() {
			var putMarkers = function(map) {
				var bounds;

				var points = ${jsonArray};

				var len = points.length;

				if (len) {
					bounds = new google.maps.LatLngBounds();

					for (var i = 0; i < len; i++) {
						var point = points[i];

						var marker = new google.maps.Marker(
							{
								icon: point.icon,
								map: map,
								position: new google.maps.LatLng(point.latitude, point.longitude),
								title: point.title
							}
						);

						bounds.extend(marker.position);

						(function(marker) {
							var infoWindow = new google.maps.InfoWindow(
								{
									content: point.abstract || point.title
								}
							);

							google.maps.event.addListener(
								marker,
								'click',
								function() {
									infoWindow.open(map, marker);
								}
							);
						})(marker);
					}
				}

				return bounds;
			};

			var drawMap = function(mapOptions) {
				var map = new google.maps.Map(document.getElementById('${renderResponse.getNamespace()}mapCanvas'), mapOptions);

				var bounds = putMarkers(map);

				if (bounds) {
					map.fitBounds(bounds);
					map.panToBounds(bounds);
				}
			};

			var drawDefaultMap = function() {
				drawMap(
					{
						center: new google.maps.LatLng(${defaultLatitude}, ${defaultLongitude}),
						zoom: 8
					}
				);
			};

			Liferay.Util.getGeolocation(
				function(latitude, longitude) {
					drawMap(
						{
							center: new google.maps.LatLng(latitude, longitude),
							zoom: 8
						}
					);
				},
				drawDefaultMap
			);
		})();
	</@liferay_aui.script>
</#if>

<#if mapsAPIProvider = "openStreetMap">
	<style type="text/css">
		#${renderResponse.getNamespace()}assetEntryAbstract {
			min-width: 400px;
			overflow: auto;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .asset-entry-abstract-image {
			float: left;
			margin-right: 2em;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .asset-entry-abstract-image img {
			display: block;
		}

		#${renderResponse.getNamespace()}assetEntryAbstract .taglib-icon {
			float: right;
		}

		#${renderResponse.getNamespace()}mapCanvas {
			min-height: 400px;
		}
	</style>

	<link href="${themeDisplay.getProtocol()}://cdn.leafletjs.com/leaflet-0.7.2/leaflet.css" rel="stylesheet" />

	<script src="${themeDisplay.getProtocol()}://cdn.leafletjs.com/leaflet-0.7.2/leaflet.js"></script>

	<@liferay_aui.script>
		(function() {
			var putMarkers = function(map) {
				var bounds;

				L.tileLayer(
					'${themeDisplay.getProtocol()}://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
					{
						attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					}
				).addTo(map);

				var points = ${jsonArray};

				var len = points.length;

				if (len) {
					bounds = L.latLngBounds([]);

					for (var i = 0; i < len; i++) {
						var point = points[i];

						var latLng = L.latLng(point.latitude, point.longitude);

						L.marker(latLng).addTo(map).bindPopup(
							point.abstract,
							{
								maxWidth: 500
							}
						);

						bounds.extend(latLng);
					}
				}

				return bounds;
			};

			var drawMap = function(lat, lng) {
				var map = L.map('${renderResponse.getNamespace()}mapCanvas').setView([lat, lng], 8);

				var bounds = putMarkers(map);

				if (bounds) {
					map.fitBounds(bounds);
				}
			};

			var drawDefaultMap = function() {
				drawMap(${defaultLatitude}, ${defaultLongitude});
			};

			Liferay.Util.getGeolocation(drawMap, drawDefaultMap);
		})();
	</@liferay_aui.script>
</#if>

<#macro getAbstract asset>
	<div class="asset-entry-abstract" id="${renderResponse.getNamespace()}assetEntryAbstract">
		<#assign showEditURL = paramUtil.getBoolean(renderRequest, "showEditURL", true) />

		<#assign assetRenderer = asset.getAssetRenderer() />

		<#if showEditURL && assetRenderer.hasEditPermission(permissionChecker)>
			<#assign redirectURL = renderResponse.createLiferayPortletURL(themeDisplay.getPlid(), themeDisplay.getPortletDisplay().getId(), "RENDER_PHASE", false) />

			${redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect")}

			<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("POP_UP"), redirectURL) />

			<#assign taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "editAsset', title: '" + htmlUtil.escapeJS(languageUtil.format(locale, "edit-x", htmlUtil.escape(assetRenderer.getTitle(locale)), false)) + "', uri:'" + htmlUtil.escapeJS(editPortletURL.toString()) + "'});" />

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