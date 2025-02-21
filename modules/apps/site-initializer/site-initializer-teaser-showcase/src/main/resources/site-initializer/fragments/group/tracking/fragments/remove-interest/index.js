function getCookie(name) {
	const cookieName = `${name}=`;

	for (let cookie of decodeURIComponent(document.cookie).split(';')) {
		cookie = cookie.trimStart();

		if (cookie.startsWith(cookieName)) {
			return cookie.substring(cookieName.length);
		}
	}

	return "";
}

function eraseCookie(name) {
	if (getCookie(name)) {
		document.cookie = `${name}=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT`;
	}
}

eraseCookie('Interest');