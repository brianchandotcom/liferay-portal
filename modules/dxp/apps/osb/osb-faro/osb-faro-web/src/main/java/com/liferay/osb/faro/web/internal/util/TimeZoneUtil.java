/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.web.internal.model.display.contacts.TimeZoneDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Geyson Silva
 * @author André Miranda
 */
public class TimeZoneUtil {

	public static final String UTC_TIME_ZONE_ID = "UTC";

	public static TimeZoneDisplay getTimeZoneDisplay(String timeZoneId) {
		ZoneId zoneId = ZoneId.of(timeZoneId);

		return new TimeZoneDisplay(
			zoneId, _timeZoneIdCountryMap.get(timeZoneId));
	}

	public static List<TimeZoneDisplay> getTimeZoneDisplays() {
		Set<Map.Entry<String, String>> timeZoneIds =
			_timeZoneIdCountryMap.entrySet();

		return TransformUtil.transform(
			timeZoneIds,
			timeZoneId -> new TimeZoneDisplay(
				ZoneId.of(timeZoneId.getKey()), timeZoneId.getValue()));
	}

	public static boolean validate(String timeZoneId) {
		try {
			return _timeZoneIdCountryMap.containsKey(timeZoneId);
		}
		catch (ZoneRulesException zoneRulesException) {
			_log.error(zoneRulesException);

			return false;
		}
	}

	private static int _compareTimeZoneId(
		String timeZoneId1, String timeZoneId2) {

		Instant instant = Instant.now();

		ZonedDateTime zonedDateTime1 = instant.atZone(ZoneId.of(timeZoneId1));
		ZonedDateTime zonedDateTime2 = instant.atZone(ZoneId.of(timeZoneId2));

		ZoneOffset zoneOffset1 = zonedDateTime1.getOffset();
		ZoneOffset zoneOffset2 = zonedDateTime2.getOffset();

		int value = zoneOffset2.compareTo(zoneOffset1);

		if (value == 0) {
			return timeZoneId1.compareTo(timeZoneId2);
		}

		return value;
	}

	private static final Log _log = LogFactoryUtil.getLog(TimeZoneUtil.class);

	private static final Map<String, String> _timeZoneIdCountryMap =
		TreeMapBuilder.<String, String>create(
			TimeZoneUtil::_compareTimeZoneId
		).put(
			"Africa/Abidjan", "Côte d’Ivoire"
		).put(
			"Africa/Accra", "Ghana"
		).put(
			"Africa/Algiers", "Algeria"
		).put(
			"Africa/Asmera", "Eritrea"
		).put(
			"Africa/Bissau", "Guinea-Bissau"
		).put(
			"Africa/Cairo", "Egypt"
		).put(
			"Africa/Casablanca", "Morocco"
		).put(
			"Africa/Ceuta", "Spain"
		).put(
			"Africa/El_Aaiun", "Morocco"
		).put(
			"Africa/Johannesburg", "South Africa"
		).put(
			"Africa/Juba", "South Sudan"
		).put(
			"Africa/Lagos", "Nigeria"
		).put(
			"Africa/Maputo", "Mozambique"
		).put(
			"Africa/Monrovia", "Liberia"
		).put(
			"Africa/Nairobi", "Kenya"
		).put(
			"Africa/Ndjamena", "Chad"
		).put(
			"Africa/Tripoli", "Libya"
		).put(
			"Africa/Tunis", "Tunisia"
		).put(
			"Africa/Windhoek", "Namibia"
		).put(
			"America/Adak", "United States"
		).put(
			"America/Anchorage", "United States"
		).put(
			"America/Araguaina", "Brazil"
		).put(
			"America/Argentina/Buenos_Aires", "Argentina"
		).put(
			"America/Argentina/Catamarca", "Argentina"
		).put(
			"America/Argentina/Cordoba", "Argentina"
		).put(
			"America/Argentina/Jujuy", "Argentina"
		).put(
			"America/Argentina/La_Rioja", "Argentina"
		).put(
			"America/Argentina/Mendoza", "Argentina"
		).put(
			"America/Argentina/Rio_Gallegos", "Argentina"
		).put(
			"America/Argentina/Salta", "Argentina"
		).put(
			"America/Argentina/San_Juan", "Argentina"
		).put(
			"America/Argentina/San_Luis", "Argentina"
		).put(
			"America/Argentina/Tucuman", "Argentina"
		).put(
			"America/Argentina/Ushuaia", "Argentina"
		).put(
			"America/Asuncion", "Paraguay"
		).put(
			"America/Atikokan", "Canada"
		).put(
			"America/Bahia", "Brazil"
		).put(
			"America/Bahia_Banderas", "Mexico"
		).put(
			"America/Barbados", "Caribbean"
		).put(
			"America/Belem", "Brazil"
		).put(
			"America/Belize", "Belize"
		).put(
			"America/Blanc-Sablon", "Canada"
		).put(
			"America/Boa_Vista", "Brazil"
		).put(
			"America/Bogota", "Colombia"
		).put(
			"America/Boise", "United States"
		).put(
			"America/Cambridge_Bay", "Canada"
		).put(
			"America/Campo_Grande", "Brazil"
		).put(
			"America/Cancun", "Mexicon"
		).put(
			"America/Caracas", "Venezuela"
		).put(
			"America/Cayenne", "French Guiana"
		).put(
			"America/Chicago", "United States"
		).put(
			"America/Chihuahua", "Mexico"
		).put(
			"America/Costa_Rica", "Costa Rica"
		).put(
			"America/Creston", "United States"
		).put(
			"America/Cuiaba", "Brazil"
		).put(
			"America/Curacao", "Curacao"
		).put(
			"America/Danmarkshavn", "Greenland"
		).put(
			"America/Dawson", "Canada"
		).put(
			"America/Dawson_Creek", "Canada"
		).put(
			"America/Denver", "United States"
		).put(
			"America/Detroit", "United States"
		).put(
			"America/Edmonton", "Canada"
		).put(
			"America/Eirunepe", "Brazil"
		).put(
			"America/El_Salvador", "El Salvador"
		).put(
			"America/Fort_Nelson", "Canada"
		).put(
			"America/Fortaleza", "Brazil"
		).put(
			"America/Glace_Bay", "Canada"
		).put(
			"America/Godthab", "Greenland"
		).put(
			"America/Goose_Bay", "Canada"
		).put(
			"America/Grand_Turk", "Turks and Caicos Islands"
		).put(
			"America/Guatemala", "Guatemala"
		).put(
			"America/Guayaquil", "Ecuador"
		).put(
			"America/Guyana", "Guyana"
		).put(
			"America/Halifax", "Canada"
		).put(
			"America/Hermosillo", "Mexico"
		).put(
			"America/Indiana/Indianapolis", "United States"
		).put(
			"America/Indiana/Knox", "United States"
		).put(
			"America/Indiana/Marengo", "United States"
		).put(
			"America/Indiana/Petersburg", "United States"
		).put(
			"America/Indiana/Tell_City", "United States"
		).put(
			"America/Indiana/Vevay", "United States"
		).put(
			"America/Indiana/Vincennes", "United States"
		).put(
			"America/Indiana/Winamac", "United States"
		).put(
			"America/Inuvik", "Canada"
		).put(
			"America/Iqaluit", "Canada"
		).put(
			"America/Jamaica", "Jamaica"
		).put(
			"America/Juneau", "United States"
		).put(
			"America/Kentucky/Louisville", "United States"
		).put(
			"America/Kentucky/Monticello", "United States"
		).put(
			"America/La_Paz", "Bolivia"
		).put(
			"America/Lima", "Peru"
		).put(
			"America/Los_Angeles", "United States"
		).put(
			"America/Maceio", "Brazil"
		).put(
			"America/Managua", "Nicaragua"
		).put(
			"America/Manaus", "Brazil"
		).put(
			"America/Martinique", "Martinique"
		).put(
			"America/Matamoros", "Mexico"
		).put(
			"America/Mazatlan", "Mexico"
		).put(
			"America/Menominee", "United States"
		).put(
			"America/Merida", "Mexico"
		).put(
			"America/Metlakatla", "United States"
		).put(
			"America/Mexico_City", "Mexico"
		).put(
			"America/Miquelon", "St. Pierre & Miquelon"
		).put(
			"America/Moncton", "Canada"
		).put(
			"America/Monterrey", "Mexico"
		).put(
			"America/Montevideo", "Uruguay"
		).put(
			"America/Nassau", "Bahamas"
		).put(
			"America/New_York", "United States"
		).put(
			"America/Nipigon", "Canada"
		).put(
			"America/Nome", "United States"
		).put(
			"America/Noronha", "Brazil"
		).put(
			"America/North_Dakota/Beulah", "United States"
		).put(
			"America/North_Dakota/Center", "United States"
		).put(
			"America/North_Dakota/New_Salem", "United States"
		).put(
			"America/Ojinaga", "Mexico"
		).put(
			"America/Panama", "Panama"
		).put(
			"America/Pangnirtung", "Canada"
		).put(
			"America/Paramaribo", "Suriname"
		).put(
			"America/Phoenix", "United States"
		).put(
			"America/Port-au-Prince", "Haiti"
		).put(
			"America/Port_of_Spain", "Trinidad and Tobago"
		).put(
			"America/Porto_Velho", "Brazil"
		).put(
			"America/Puerto_Rico", "United States"
		).put(
			"America/Punta_Arenas", "Chile"
		).put(
			"America/Rainy_River", "Canada"
		).put(
			"America/Rankin_Inlet", "Canada"
		).put(
			"America/Recife", "Brazil"
		).put(
			"America/Regina", "Canada"
		).put(
			"America/Resolute", "Canada"
		).put(
			"America/Rio_Branco", "Brazil"
		).put(
			"America/Santarem", "Brazil"
		).put(
			"America/Santiago", "Chile"
		).put(
			"America/Santo_Domingo", "Dominican Republic"
		).put(
			"America/Sao_Paulo", "Brazil"
		).put(
			"America/Scoresbysund", "Greenland"
		).put(
			"America/Sitka", "United States"
		).put(
			"America/St_Johns", "Canada"
		).put(
			"America/Swift_Current", "Canada"
		).put(
			"America/Tegucigalpa", "Honduras"
		).put(
			"America/Thule", "Greenland"
		).put(
			"America/Thunder_Bay", "Canada"
		).put(
			"America/Tijuana", "Mexico"
		).put(
			"America/Toronto", "Canada"
		).put(
			"America/Vancouver", "Canada"
		).put(
			"America/Whitehorse", "Canada"
		).put(
			"America/Winnipeg", "Canada"
		).put(
			"America/Yakutat", "United States"
		).put(
			"America/Yellowknife", "Canada"
		).put(
			"Antarctica/Casey", "Antarctica"
		).put(
			"Antarctica/Davis", "Antarctica"
		).put(
			"Antarctica/DumontDUrville", "Antarctica"
		).put(
			"Antarctica/Macquarie", "Antarctica"
		).put(
			"Antarctica/Mawson", "Antarctica"
		).put(
			"Antarctica/Palmer", "Antarctica"
		).put(
			"Antarctica/Rothera", "Antarctica"
		).put(
			"Antarctica/Syowa", "Antarctica"
		).put(
			"Antarctica/Troll", "Antarctica"
		).put(
			"Antarctica/Vostok", "Antarctica"
		).put(
			"Asia/Almaty", "Kazakhstan"
		).put(
			"Asia/Amman", "Jordan"
		).put(
			"Asia/Anadyr", "Russia"
		).put(
			"Asia/Aqtau", "Kazakhstan"
		).put(
			"Asia/Aqtobe", "Kazakhstan"
		).put(
			"Asia/Ashgabat", "Turkmenistan"
		).put(
			"Asia/Atyrau", "Kazakhstan"
		).put(
			"Asia/Baghdad", "Iraq"
		).put(
			"Asia/Baku", "Azerbaijan"
		).put(
			"Asia/Bangkok", "Thailand"
		).put(
			"Asia/Barnaul", "Russia"
		).put(
			"Asia/Beirut", "Lebanon"
		).put(
			"Asia/Bishkek", "Kyrgyzstan"
		).put(
			"Asia/Brunei", "Brunei"
		).put(
			"Asia/Chita", "Russia"
		).put(
			"Asia/Choibalsan", "Mongolia"
		).put(
			"Asia/Colombo", "Sri Lanka"
		).put(
			"Asia/Dhaka", "Bangladesh"
		).put(
			"Asia/Dili", "Timor-Leste"
		).put(
			"Asia/Dubai", "United Arab Emirates"
		).put(
			"Asia/Dushanbe", "Tajikistan"
		).put(
			"Asia/Famagusta", "Cyprus"
		).put(
			"Asia/Gaza", "Palestine"
		).put(
			"Asia/Hebron", "Palestine"
		).put(
			"Asia/Ho_Chi_Minh", "Vietnam"
		).put(
			"Asia/Hong_Kong", "China"
		).put(
			"Asia/Hovd", "Mongolia"
		).put(
			"Asia/Irkutsk", "Russia"
		).put(
			"Asia/Jakarta", "Indonesia"
		).put(
			"Asia/Jayapura", "Indonesia"
		).put(
			"Asia/Jerusalem", "Israel"
		).put(
			"Asia/Kabul", "Afghanistan"
		).put(
			"Asia/Kamchatka", "Russia"
		).put(
			"Asia/Karachi", "Pakistan"
		).put(
			"Asia/Kathmandu", "Nepal"
		).put(
			"Asia/Khandyga", "Russia"
		).put(
			"Asia/Kolkata", "India"
		).put(
			"Asia/Krasnoyarsk", "Russia"
		).put(
			"Asia/Kuala_Lumpur", "Malaysia"
		).put(
			"Asia/Kuching", "Malaysia"
		).put(
			"Asia/Macau", "China"
		).put(
			"Asia/Magadan", "Russia"
		).put(
			"Asia/Makassar", "Indonesia"
		).put(
			"Asia/Manila", "Philippines"
		).put(
			"Asia/Nicosia", "Cyprus"
		).put(
			"Asia/Novokuznetsk", "Russia"
		).put(
			"Asia/Novosibirsk", "Russia"
		).put(
			"Asia/Omsk", "Russia"
		).put(
			"Asia/Oral", "Kazakhstan"
		).put(
			"Asia/Pontianak", "Indonesia"
		).put(
			"Asia/Qatar", "Qatar"
		).put(
			"Asia/Qyzylorda", "Kazakhstan"
		).put(
			"Asia/Riyadh", "Saudi Arabia"
		).put(
			"Asia/Sakhalin", "Russia"
		).put(
			"Asia/Samarkand", "Uzbekistan"
		).put(
			"Asia/Seoul", "South Korea"
		).put(
			"Asia/Shanghai", "China"
		).put(
			"Asia/Singapore", "Singapore"
		).put(
			"Asia/Srednekolymsk", "Russia"
		).put(
			"Asia/Taipei", "Taiwan"
		).put(
			"Asia/Tashkent", "Uzbekistan"
		).put(
			"Asia/Tbilisi", "Georgia"
		).put(
			"Asia/Thimphu", "Bhutan"
		).put(
			"Asia/Tokyo", "Japan"
		).put(
			"Asia/Tomsk", "Russia"
		).put(
			"Asia/Ulaanbaatar", "Mongolia"
		).put(
			"Asia/Urumqi", "China"
		).put(
			"Asia/Ust-Nera", "Russia"
		).put(
			"Asia/Vladivostok", "Russia"
		).put(
			"Asia/Yakutsk", "Russia"
		).put(
			"Asia/Yangon", "Myanmar"
		).put(
			"Asia/Yekaterinburg", "Russia"
		).put(
			"Asia/Yerevan", "Armenia"
		).put(
			"Atlantic/Azores", "Portugal"
		).put(
			"Atlantic/Bermuda", "United Kingdom"
		).put(
			"Atlantic/Canary", "Spain"
		).put(
			"Atlantic/Cape_Verde", "Cabo Verde"
		).put(
			"Atlantic/Faroe", "Denmark"
		).put(
			"Atlantic/Madeira", "Portugal"
		).put(
			"Atlantic/Reykjavik", "Iceland"
		).put(
			"Atlantic/South_Georgia", "United Kingdom"
		).put(
			"Atlantic/Stanley", "United Kingdom"
		).put(
			"Australia/Adelaide", "Australia"
		).put(
			"Australia/Brisbane", "Australia"
		).put(
			"Australia/Broken_Hill", "Australia"
		).put(
			"Australia/Currie", "Australia"
		).put(
			"Australia/Darwin", "Australia"
		).put(
			"Australia/Eucla", "Australia"
		).put(
			"Australia/Hobart", "Australia"
		).put(
			"Australia/Lindeman", "Australia"
		).put(
			"Australia/Lord_Howe", "Australia"
		).put(
			"Australia/Melbourne", "Australia"
		).put(
			"Australia/Perth", "Australia"
		).put(
			"Australia/Sydney", "Australia"
		).put(
			"Europe/Amsterdam", "Netherlands"
		).put(
			"Europe/Andorra", "Andorra"
		).put(
			"Europe/Astrakhan", "Russia"
		).put(
			"Europe/Athens", "Greece"
		).put(
			"Europe/Belgrade", "Serbia"
		).put(
			"Europe/Berlin", "Germany"
		).put(
			"Europe/Brussels", "Belgium"
		).put(
			"Europe/Bucharest", "Romania"
		).put(
			"Europe/Budapest", "Hungary"
		).put(
			"Europe/Chisinau", "Moldova"
		).put(
			"Europe/Copenhagen", "Denmark"
		).put(
			"Europe/Dublin", "Ireland"
		).put(
			"Europe/Gibraltar", "United Kingdom"
		).put(
			"Europe/Helsinki", "Finland"
		).put(
			"Europe/Istanbul", "Turkey"
		).put(
			"Europe/Kaliningrad", "Russia"
		).put(
			"Europe/Kiev", "Ukraine"
		).put(
			"Europe/Kirov", "Russia"
		).put(
			"Europe/Lisbon", "Portugal"
		).put(
			"Europe/London", "United Kingdom"
		).put(
			"Europe/Luxembourg", "Luxembourg"
		).put(
			"Europe/Madrid", "Spain"
		).put(
			"Europe/Malta", "Malta"
		).put(
			"Europe/Minsk", "Russia"
		).put(
			"Europe/Monaco", "Monaco"
		).put(
			"Europe/Moscow", "Russia"
		).put(
			"Europe/Nicosia", "Cyprus"
		).put(
			"Europe/Oslo", "Norway"
		).put(
			"Europe/Paris", "France"
		).put(
			"Europe/Prague", "Czech Republic"
		).put(
			"Europe/Riga", "Latvia"
		).put(
			"Europe/Rome", "Italy"
		).put(
			"Europe/Samara", "Russia"
		).put(
			"Europe/Saratov", "Russia"
		).put(
			"Europe/Simferopol", "Russia"
		).put(
			"Europe/Sofia", "Bulgaria"
		).put(
			"Europe/Stockholm", "Sweden"
		).put(
			"Europe/Tallinn", "Estonia"
		).put(
			"Europe/Tirane", "Albania"
		).put(
			"Europe/Ulyanovsk", "Russia"
		).put(
			"Europe/Uzhgorod", "Ukraine"
		).put(
			"Europe/Vienna", "Austria"
		).put(
			"Europe/Vilnius", "Lithuania"
		).put(
			"Europe/Volgograd", "Russia"
		).put(
			"Europe/Warsaw", "Poland"
		).put(
			"Europe/Zaporozhye", "Ukraine"
		).put(
			"Europe/Zurich", "Switzerland"
		).put(
			"Indian/Chagos", "British Indian Ocean Territory"
		).put(
			"Indian/Christmas", "Christmas Island"
		).put(
			"Indian/Cocos", "Australia"
		).put(
			"Indian/Kerguelen", "French Southern"
		).put(
			"Indian/Mahe", "Seychelles"
		).put(
			"Indian/Maldives", "Maldives"
		).put(
			"Indian/Mauritius", "Mauritius"
		).put(
			"Indian/Reunion", "France"
		).put(
			"Pacific/Apia", "Samoa"
		).put(
			"Pacific/Auckland", "New Zealand"
		).put(
			"Pacific/Bougainville", "Papua New Guinea"
		).put(
			"Pacific/Chatham", "New Zealand"
		).put(
			"Pacific/Chuuk", "Micronesia"
		).put(
			"Pacific/Easter", "Chile"
		).put(
			"Pacific/Efate", "Vanuatu"
		).put(
			"Pacific/Enderbury", "Kiribati"
		).put(
			"Pacific/Fakaofo", "Tokelau"
		).put(
			"Pacific/Fiji", "Fiji"
		).put(
			"Pacific/Funafuti", "Tuvalu"
		).put(
			"Pacific/Galapagos", "Ecuador"
		).put(
			"Pacific/Gambier", "French Polynesia"
		).put(
			"Pacific/Guadalcanal", "Solomon Islands"
		).put(
			"Pacific/Guam", "Guam"
		).put(
			"Pacific/Honolulu", "United States"
		).put(
			"Pacific/Kiritimati", "Kiribati"
		).put(
			"Pacific/Kosrae", "Micronesia"
		).put(
			"Pacific/Kwajalein", "Marshall Islands"
		).put(
			"Pacific/Majuro", "Marshall Islands"
		).put(
			"Pacific/Marquesas", "French Polynesia"
		).put(
			"Pacific/Nauru", "Nauru"
		).put(
			"Pacific/Niue", "Niue"
		).put(
			"Pacific/Norfolk", "Norfolk Island"
		).put(
			"Pacific/Noumea", "New Caledonia"
		).put(
			"Pacific/Pago_Pago", "Samoa"
		).put(
			"Pacific/Palau", "Micronesia"
		).put(
			"Pacific/Pitcairn", "Pitcairn"
		).put(
			"Pacific/Pohnpei", "Micronesia"
		).put(
			"Pacific/Port_Moresby", "Papua New Guinea"
		).put(
			"Pacific/Rarotonga", "Cook Islands"
		).put(
			"Pacific/Tahiti", "French Polynesia"
		).put(
			"Pacific/Tarawa", "Kiribati"
		).put(
			"Pacific/Tongatapu", "Tonga"
		).put(
			"Pacific/Wake", "United States"
		).put(
			"Pacific/Wallis", "Wallis & Futuna"
		).put(
			"UTC", "UTC"
		).build();

}