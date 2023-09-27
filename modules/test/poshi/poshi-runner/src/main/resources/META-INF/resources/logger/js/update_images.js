function getFirstImageURL() {
	var images = document.images;

	for (var i = 0; i < images.length; i++) {
		var url = images[i].src;

		if (url.startsWith("http")) {
			return url;
		}
	}

	return;
}

function updateAuthUser(url, authuser) {
	var regExp = new RegExp("authuser=[^&]+");

	if (url.match(regExp)) {
		url = url.replace(regExp, "authuser=" + authuser);
	}
	else if (url.includes("?")) {
		url += "&authuser=" + authuser;
	}
	else {
		url += "?authuser=" + authuser;
	}

	return url;
}

function updateImages() {
	var firstImageURL = getFirstImageURL();

	if (firstImageURL == null) {
		return;
	}

	for (var i = 0; i < 5; i++) {
		var image = new Image();

		image.authuser = i;

		image.onload = function() {
			if (this.width <= 0) {
				return;
			}

			var images = document.images;

			for (var i = 0; i < images.length; i++) {
				var url = images[i].src;

				if (!url.startsWith("http")) {
					continue;
				}

				images[i].src = updateAuthUser(url, this.authuser);
			}
		}

		image.src = updateAuthUser(firstImageURL, image.authuser);
	}
}

updateImages();