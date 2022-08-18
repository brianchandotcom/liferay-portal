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

/*
 * Step 1: Obtain Authorization Code from Liferay Configured Application with ClientID (Callback URL must be this application)
 */
function getAuthorizationCode() {
	if ($('#authForm').valid()) {
		const clientId = $('#clientId').val();
		const liferayUrl = $('#liferayUrl').val();
		const redirectUri = encodeURIComponent(
			window.location.href +
				'?url=' +
				liferayUrl +
				'&client_id=' +
				clientId
		);
		window.location.href =
			$('#liferayUrl').val() +
			$('#authorizeUrl').val() +
			'?client_id={0}&response_type=code&redirect_uri={1}'
				.replace('{0}', clientId)
				.replace('{1}', redirectUri);
	}
}

/*
 * Function to get needed data when you already have a token
 */
function copyModalData() {
	if ($('#modalForm').valid()) {
		$('#tokenLiferayUrl').val($('#modalLiferayUrl').val());
		$('#tokenClientId').val($('#modalClientId').val());
		$('#clientSecretId').val($('#modalClientSecret').val());

		$('#modal').modal('hide');
		$('#collapseOne').hide();
		$('#collapseTwo').hide();
		$('#collapseThree').show();
	}
}

$(document).ready(() => {
	$('#callbackURI').text(location.origin + location.pathname);

	/*
	 * Recover Auth Code from URL and Obtain Token
	 */
	const urlParams = function getCodeParameter(parameterName) {
		const pageUrl = window.location.search.substring(1);
		const variablesUrl = pageUrl.split('&');
		for (let i = 0; i < variablesUrl.length; i++) {
			const parameter = variablesUrl[i].split('=');
			if (parameter[0] === parameterName) {
				return parameter[1];
			}
		}
	};

	if (urlParams('code')) {
		$('#collapseOne').hide();
		$('h2#step1 > button').css('color', 'green');
		$('#collapseTwo').show();

		$('#code').val(urlParams('code'));
		$('#tokenLiferayUrl').val(unescape(urlParams('url')));
		$('#tokenClientId').val(urlParams('client_id'));
	}

	/*
	 * Function to generate parameter fields dynamically
	 */
	let parameterCount = 1;
	$('#addParameter').click((event) => {
		event.preventDefault();
		const addName = 'paramName' + parameterCount;
		const addValue = 'paramValue' + parameterCount;

		const newName = $(
			'<div class="col"><input type="text" id="' +
				addName +
				'" placeholder="Name" class="form-control form-control-sm" /></div>'
		);
		const newValue = $(
			'<div class="col"><input type="text" id="' +
				addValue +
				'" placeholder="Value" class="form-control form-control-sm" /></div>'
		);

		const newSection = $(
			'<div class="row" id="param' + parameterCount + '">'
		);
		newSection.append(newName);
		newSection.append(newValue);
		$('#parametersList').append(newSection);

		parameterCount++;
	});
});

/*
 * Step 2: Obtain OAuth2 Token
 */
function getToken() {
	if ($('#tokenForm').valid()) {
		const locationUrl =
			location.protocol + '//' + location.host + location.pathname;
		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			data: {
				client_id: $('#tokenClientId').val(),
				client_secret: $('#clientSecretId').val(),
				code: $('#code').val(),
				grant_type: 'authorization_code',
				redirect_uri:
					locationUrl +
					'?url=' +
					$('#tokenLiferayUrl').val() +
					'&client_id=' +
					$('#tokenClientId').val(),
			},
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				console.log(data);
			},
			headers: {
				Accept: 'application/json',
			},
			method: 'POST',
			success(data) {
				$('#token').val(data.access_token);
				$('#refreshToken').val(data.refresh_token);
				$('#collapseTwo').hide();
				$('h2#step2 > button').css('color', 'green');
				$('#collapseThree').show();
			},
			url: $('#tokenLiferayUrl').val() + $('#tokenUrl').val(),
		});
	}
}

/*
 * Function for Introspect the Access Token
 */
function introspectAccessToken(event) {
	event.preventDefault();
	if ($('#token').val().trim() !== '') {
		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			crossDomain: true,
			data: {
				client_id: $('#tokenClientId').val(),
				client_secret: $('#clientSecretId').val(),
				token: $('#token').val(),
				token_type_hint: 'access_token',
			},
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				console.log(data);
			},
			headers: {
				Accept: 'application/json',
			},
			method: 'POST',
			success(data) {
				$('#result').html(JSON.stringify(data));
			},

			url: $('#tokenLiferayUrl').val() + '/o/oauth2/introspect',
		});
	}
	else {
		alert('Please, insert a valid Access Token');
	}
}

/*
 * Function for Introspect the Refresh Token
 */
function introspectRefreshToken(event) {
	event.preventDefault();
	if ($('#refreshToken').val().trim() !== '') {
		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			crossDomain: true,
			data: {
				client_id: $('#tokenClientId').val(),
				client_secret: $('#clientSecretId').val(),
				token: $('#refreshToken').val(),
				token_type_hint: 'refresh_token',
			},
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				console.log(data);
			},
			headers: {
				Accept: 'application/json',
			},
			method: 'POST',
			success(data) {
				$('#result').html(JSON.stringify(data));
			},
			url: $('#tokenLiferayUrl').val() + '/o/oauth2/introspect',
		});
	}
	else {
		alert('Please, insert a valid Refresh Token');
	}
}

/*
 * Refresh Token
 */
function refreshAccessToken(event) {
	event.preventDefault();
	if ($('#refreshToken').val().trim() !== '') {
		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			crossDomain: true,
			data: {
				client_id: $('#tokenClientId').val(),
				client_secret: $('#clientSecretId').val(),
				grant_type: 'refresh_token',
				refresh_token: $('#refreshToken').val(),
			},
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				console.log(data);
			},
			headers: {
				Accept: 'application/json',
			},
			method: 'POST',
			success(data) {
				$('#token').val(data.access_token);
				$('#refreshToken').val(data.refresh_token);
			},
			url: $('#tokenLiferayUrl').val() + $('#tokenUrl').val(),
		});
	}
	else {
		alert('Please, insert a valid Refresh Token');
	}
}

/*
 * Step 3: Make Request
 */
function request(event) {
	event.preventDefault();
	$('#result').html('');
	const paramsData = getParamsData();
	const token = $('#token').val();
	if (token.trim() !== '') {
		$.ajax({
			contentType: $('#contentType').val(),
			crossDomain: true,
			data: JSON.stringify(paramsData),
			dataType: 'json',
			error(jqXHR) {
				let msg = '';
				if (jqXHR.status === 403) {
					msg = '403 - Unauthorized!';
				}
				else if (jqXHR.status === 404) {
					msg = '404 - Not Found';
				}
				else if (jqXHR.status === 405) {
					msg = '405 - Not Allowed';
				}
				else if (jqXHR.status === 415) {
					msg = '415 - Unsupported Media Type';
				}
				else {
					msg = 'Error: ' + jqXHR.responseText;
				}
				alert(msg);
			},
			headers: {
				Authorization: 'Bearer ' + token,
			},
			method: $('#methodType').val(),
			success(data) {
				$('#result').html(JSON.stringify(data));
			},
			url: $('#url').val(),
		});
	}
	else {
		alert('Please, insert a valid Access Token');
	}
}

/*
 * Check method type request to print parameters list
 */
function checkMethod(event) {
	event.preventDefault();
	const method = $('#methodType').val();
	if (method !== 'GET') {
		$('#parametersList').show();
		$('#addParameter').show();
	}
	else {
		$('#parametersList').hide();
		$('#addParameter').hide();
	}
}

/*
 * Generate JSON for send parameters data
 */
function getParamsData() {
	let data;
	if ($('#parametersList').is(':visible')) {
		data = {};
		const numParams = $('#parametersList').find($('input')).length / 2;
		for (let i = 0; i < numParams; i++) {
			const name = $('#paramName' + i).val();
			const value = $('#paramValue' + i).val();
			if (name !== '' && value !== '') {
				data[name] = value;
			}
		}
	}

	return data;
}
