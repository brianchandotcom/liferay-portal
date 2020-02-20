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

export function getCKEditorConfig() {
	const config = {};
	config.toolbarGroups = [
		{groups: ['basicstyles', 'cleanup'], name: 'basicstyles'},
		{
			groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'],
			name: 'paragraph'
		},
		{groups: ['links'], name: 'links'},
		{groups: ['clipboard', 'undo'], name: 'clipboard'},
		{groups: ['mode', 'document', 'doctools'], name: 'document'},
		{
			groups: ['find', 'selection', 'spellchecker', 'editing'],
			name: 'editing'
		}
	];
	config.removeButtons =
		'About,Anchor,BGColor,BidiLtr,BidiRtl,Button,Checkbox,Copy,CopyFormatting,CreateDiv,Cut,Find,Flash,Font,FontSize,Form,Format,HiddenField,HorizontalRule,Iframe,Image,ImageButton,JustifyBlock,JustifyCenter,JustifyLeft,JustifyRight,Language,Maximize,NewPage,PageBreak,Paste,PasteFromWord,PasteText,Preview,Print,Radio,RemoveFormat,Replace,Save,Select,SelectAll,ShowBlocks,Smiley,Source,SpecialChar,Styles,Subscript,Superscript,Table,Templates,TextColor,TextField,Textarea';

	return config;
}

export function dateToInternationalHuman(
	ISOString,
	localeKey = navigator.language
) {
	const date = new Date(ISOString);

	const options = {
		day: 'numeric',
		hour: '2-digit',
		minute: '2-digit',
		month: 'short'
	};

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
		year: '2-digit'
	});

	return intl.format(date);
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
			Math.round(elapsed / 1000)
		]);
	}
	else if (elapsed < msPerHour) {
		return lang.sub(Liferay.Language.get('asked-x-minutes-ago-by'), [
			Math.round(elapsed / msPerMinute)
		]);
	}
	else if (elapsed < msPerDay) {
		return lang.sub(Liferay.Language.get('asked-x-hours-ago-by'), [
			Math.round(elapsed / msPerHour)
		]);
	}
	else if (elapsed < msPerMonth) {
		return lang.sub(Liferay.Language.get('asked-x-days-ago-by'), [
			Math.round(elapsed / msPerDay)
		]);
	}
	else if (elapsed < msPerYear) {
		return lang.sub(Liferay.Language.get('asked-x-months-ago-by'), [
			Math.round(elapsed / msPerMonth)
		]);
	}
	else {
		return lang.sub(Liferay.Language.get('asked-x-years-ago-by'), [
			Math.round(elapsed / msPerYear)
		]);
	}
}

export function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}
