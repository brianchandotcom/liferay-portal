/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cancelDebounce, debounce} from 'frontend-js-web';
import {useRef} from 'react';

import lang from './lang.es';

export function dateToInternationalHuman(
	ISOString,
	localeKey = navigator.language
) {
	const date = new Date(ISOString);

	const options = {
		day: 'numeric',
		hour: '2-digit',
		minute: '2-digit',
		month: 'short',
	};

	if (date.getFullYear() !== new Date().getFullYear()) {
		options.year = 'numeric';
	}

	const intl = new Intl.DateTimeFormat(localeKey, options);

	return intl.format(date);
}

export function dateToBriefInternationalHuman(
	ISOString,
	localeKey = navigator.language
) {
	const date = new Date(ISOString);

	const intl = new Intl.DateTimeFormat(localeKey, {
		day: '2-digit',
		month: '2-digit',
		year: '2-digit',
	});

	return intl.format(date);
}

export function deleteCacheVariables(cache, parameter) {
	Object.keys(cache.data.data).forEach(
		(key) => key.match(`^${parameter}`) && cache.data.delete(key)
	);
}

export function timeDifference(previous, current = new Date()) {
	const msPerMinute = 60 * 1000;
	const msPerHour = msPerMinute * 60;
	const msPerDay = msPerHour * 24;
	const msPerMonth = msPerDay * 30;
	const msPerYear = msPerDay * 365;

	const elapsed = current - new Date(previous);

	if (elapsed < msPerMinute) {
		return lang.sub(Liferay.Language.get('asked-x-seconds-ago-by'), [
			Math.round(elapsed / 1000),
		]);
	}
	else if (elapsed < msPerHour) {
		return lang.sub(Liferay.Language.get('asked-x-minutes-ago-by'), [
			Math.round(elapsed / msPerMinute),
		]);
	}
	else if (elapsed < msPerDay) {
		return lang.sub(Liferay.Language.get('asked-x-hours-ago-by'), [
			Math.round(elapsed / msPerHour),
		]);
	}
	else if (elapsed < msPerMonth) {
		return lang.sub(Liferay.Language.get('asked-x-days-ago-by'), [
			Math.round(elapsed / msPerDay),
		]);
	}
	else if (elapsed < msPerYear) {
		return lang.sub(Liferay.Language.get('asked-x-months-ago-by'), [
			Math.round(elapsed / msPerMonth),
		]);
	}
	else {
		return lang.sub(Liferay.Language.get('asked-x-years-ago-by'), [
			Math.round(elapsed / msPerYear),
		]);
	}
}

export function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

export function normalizeRating(aggregateRating) {
	return (
		aggregateRating &&
		Math.trunc(
			aggregateRating.ratingCount *
				normalize(aggregateRating.ratingAverage)
		)
	);
}

export function normalize(ratingValue) {
	return ratingValue * 2 - 1;
}

export function stringToSlug(text) {
	const whiteSpaces = /\s+/g;

	return text.replace(whiteSpaces, '-').toLowerCase();
}

export function slugToText(slug) {
	const hyphens = /-+/g;

	return slug.replace(hyphens, ' ').toLowerCase();
}

export function historyPushWithSlug(push) {
	return (url) => push(stringToSlug(url));
}

export function stripHTML(text) {
	if (!text) {
		return '';
	}

	const htmlTags = /<([^>]+>)/g;
	const nonBreakableSpace = '&nbsp;';
	const newLines = /\r?\n|\r/g;

	return (
		text
			.replace(htmlTags, '')
			.replace(nonBreakableSpace, ' ')
			.replace(newLines, '') || ''
	);
}

export function getFullPath() {
	return window.location.href.substring(0, window.location.href.indexOf('#'));
}

export function getBasePath() {
	return window.location.href.substring(
		window.location.origin.length,
		window.location.href.indexOf('#')
	);
}

export function getContextLink(url) {
	return {
		headers: {
			Link: `${getFullPath()}?redirectTo=/%23/questions/${url}/`,
		},
	};
}

export function isWebCrawler() {
	const crawlerPattern =
		'(googlebot/|bot|Googlebot-Mobile|Googlebot-Image|Google favicon|Mediapartners-Google|bingbot|slurp|java|wget|curl|Commons-HttpClient|Python-urllib|libwww|httpunit|nutch|phpcrawl|msnbot|jyxobot|FAST-WebCrawler|FAST Enterprise Crawler|biglotron|teoma|convera|seekbot|gigablast|exabot|ngbot|ia_archiver|GingerCrawler|webmon |httrack|webcrawler|grub.org|UsineNouvelleCrawler|antibot|netresearchserver|speedy|fluffy|bibnum.bnf|findlink|msrbot|panscient|yacybot|AISearchBot|IOI|ips-agent|tagoobot|MJ12bot|dotbot|woriobot|yanga|buzzbot|mlbot|yandexbot|purebot|Linguee Bot|Voyager|CyberPatrol|voilabot|baiduspider|citeseerxbot|spbot|twengabot|postrank|turnitinbot|scribdbot|page2rss|sitebot|linkdex|Adidxbot|blekkobot|ezooms|dotbot|Mail.RU_Bot|discobot|heritrix|findthatfile|europarchive.org|NerdByNature.Bot|sistrix crawler|ahrefsbot|Aboundex|domaincrawler|wbsearchbot|summify|ccbot|edisterbot|seznambot|ec2linkfinder|gslfbot|aihitbot|intelium_bot|facebookexternalhit|yeti|RetrevoPageAnalyzer|lb-spider|sogou|lssbot|careerbot|wotbox|wocbot|ichiro|DuckDuckBot|lssrocketcrawler|drupact|webcompanycrawler|acoonbot|openindexspider|gnam gnam spider|web-archive-net.com.bot|backlinkcrawler|coccoc|integromedb|content crawler spider|toplistbot|seokicks-robot|it2media-domain-crawler|ip-web-crawler.com|siteexplorer.info|elisabot|proximic|changedetection|blexbot|arabot|WeSEE:Search|niki-bot|CrystalSemanticsBot|rogerbot|360Spider|psbot|InterfaxScanBot|Lipperhey SEO Service|CC Metadata Scaper|g00g1e.net|GrapeshotCrawler|urlappendbot|brainobot|fr-crawler|binlar|SimpleCrawler|Livelapbot|Twitterbot|cXensebot|smtbot|bnf.fr_bot|A6-Indexer|ADmantX|Facebot|Twitterbot|OrangeBot|memorybot|AdvBot|MegaIndex|SemanticScholarBot|ltx71|nerdybot|xovibot|BUbiNG|Qwantify|archive.org_bot|Applebot|TweetmemeBot|crawler4j|findxbot|SemrushBot|yoozBot|lipperhey|y!j-asr|Domain Re-Animator Bot|AddThis)';

	return new RegExp(crawlerPattern, 'i').test(navigator.userAgent);
}
