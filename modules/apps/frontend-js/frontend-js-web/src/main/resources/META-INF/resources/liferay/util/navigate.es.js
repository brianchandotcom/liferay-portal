/**
 * Performs navigation to the given url. If SPA is enabled, it will route the
 * request through the SPA engine. If not, it will simple change the document
 * location.
 * @param {!string} url Destination URL to navigate
 * @review
 */

export default function(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	}
	else {
		window.location.href = url;
	}
}