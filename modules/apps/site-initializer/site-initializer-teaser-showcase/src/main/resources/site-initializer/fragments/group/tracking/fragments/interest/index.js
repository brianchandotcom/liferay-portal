function setCookie(name, value, expirationDays) {  
	const expirationDate = new Date();

	expirationDate.setTime(expirationDate.getTime() + (expirationDays * 24 * 60 * 60 * 1000));

	const expires = "expires=" + expirationDate.toUTCString();

	document.cookie = `${name}=${value};${expires};path=/;secure`;
}

setCookie('Interest', 'Products', 1);