/**
 * Performs navigation to the given url. If SPA is enabled, it will route the
 * request through the SPA engine. If not, it will simple change the document
 * location.
 * @param {!string} url Destination URL to navigate
 * @param {?object} listeners Object with key-value pairs with callbacks for
 * specific page lifecycle events
 * @review
 */

export default function(url, listeners) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);

		if (listeners) {
			Object.keys(listeners).forEach(
				key => {
					Liferay.once(key, listeners[key]);
				}
			);
		}
	}
	else {
		window.location.href = url;
	}
}