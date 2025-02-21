function getCookie(name) {
	const cookieName = `${name}=`;
  	const decodedCookie = decodeURIComponent(document.cookie);

	const cookies = decodedCookie.split(';');

	for (let cookie of cookies) {
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