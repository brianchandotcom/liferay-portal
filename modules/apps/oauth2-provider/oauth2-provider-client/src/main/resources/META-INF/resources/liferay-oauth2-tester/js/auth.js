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
 * Function to get needed data when you already have a token
 */
// eslint-disable-next-line no-unused-vars
function copyModalData(isPKCE) {
	if ($('#modalForm').valid()) {
		$('#tokenLiferayUrl').val($('#modalLiferayUrl').val());
		$('#tokenClientId').val($('#modalClientId').val());
		if (!isPKCE) {
			$('#clientSecretId').val($('#modalClientSecret').val());
		}

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
		$('#authTab > h2 > button').css('color', 'green');
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
			'<div class="col-6"><input type="text" id="' +
				addName +
				'" placeholder="Name" class="form-control form-control-sm" /></div>'
		);
		const newValue = $(
			'<div class="col-6"><input type="text" id="' +
				addValue +
				'" placeholder="Value" class="form-control form-control-sm" /></div>'
		);

		const newSection = $(
			'<div class="form-group form-row" id="param' + parameterCount + '">'
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
// eslint-disable-next-line no-unused-vars
function getToken(isPKCE) {
	if ($('#tokenForm').valid()) {
		const locationUrl =
			location.protocol + '//' + location.host + location.pathname;
		const dataObj = {
			client_id: $('#tokenClientId').val(),
			code: $('#code').val(),
			grant_type: 'authorization_code',
			redirect_uri:
				locationUrl +
				'?url=' +
				$('#tokenLiferayUrl').val() +
				'&client_id=' +
				$('#tokenClientId').val(),
		};

		if (isPKCE) {
			dataObj.code_verifier = $('#exchangeCode').val();
		}
		else {
			dataObj.client_secret = $('#clientSecretId').val();
		}

		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			data: dataObj,
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				// eslint-disable-next-line no-console
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
				$('#tokenTab > h2 > button').css('color', 'green');
				$('#collapseThree').show();
			},
			url: $('#tokenLiferayUrl').val() + $('#tokenUrl').val(),
		});
	}
}

/*
 * Function for Introspect the Access or Refresh Token
 */
// eslint-disable-next-line no-unused-vars
function introspectToken(event, isPKCE, tokenType) {
	event.preventDefault();
	if (
		(tokenType === 'access_token' && $('#token').val().trim() !== '') ||
		(tokenType === 'refresh_token' &&
			$('#refreshToken').val().trim() !== '')
	) {
		const dataObj = {
			client_id: $('#tokenClientId').val(),
			token: $('#token').val(),
			token_type_hint: tokenType,
		};

		if (!isPKCE) {
			dataObj.client_secret = $('#clientSecretId').val();
		}

		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			crossDomain: true,
			data: dataObj,
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				// eslint-disable-next-line no-console
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
		alert('Please, insert a valid ' + tokenType);
	}
}

/*
 * Refresh Token
 */
// eslint-disable-next-line no-unused-vars
function refreshAccessToken(event, isPKCE) {
	event.preventDefault();
	if ($('#refreshToken').val().trim() !== '') {
		const dataObj = {
			client_id: $('#tokenClientId').val(),
			grant_type: 'refresh_token',
			refresh_token: $('#refreshToken').val(),
		};

		if (!isPKCE) {
			dataObj.client_secret = $('#clientSecretId').val();
		}

		$.ajax({
			contentType: 'application/x-www-form-urlencoded',
			crossDomain: true,
			data: dataObj,
			dataType: 'json',
			error(data) {
				alert("There's a problem with your authorization access");
				// eslint-disable-next-line no-console
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
// eslint-disable-next-line no-unused-vars
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
// eslint-disable-next-line no-unused-vars
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
