/* global Analytics, Liferay */

import debounce from 'metal-debounce';

export default (entryId, blogsEntryBodyId) => {
	if (window.Analytics) {
		var applicationId = 'Blogs';

		Analytics.registerMiddleware(
			function(request, analytics) {
				request.context.referrer = document.referrer;

				return request;
			}
		);

		Analytics.send(
			'VISITS',
			applicationId,
			{
				entryId
			}
		);

		var scrollSessionId = new Date().toISOString();

		var entry = document.getElementById(blogsEntryBodyId);

		var throttle = function(fn, wait) {
			var time = Date.now();

			return function() {
				if ((time + wait - Date.now()) < 0) {
					fn();

					time = Date.now();
				}
			};
		};

		var sendEvent = function() {
			var entryBoundingClientRect = entry.getBoundingClientRect();

			var depth = Math.trunc(100 * (-entryBoundingClientRect.top) / entryBoundingClientRect.height);

			if (depth >= 0 && depth <= 100) {
				Analytics.send(
					'DEPTH',
					applicationId,
					{
						depth: depth,
						entryId,
						sessionId: scrollSessionId
					}
				);
			}
		};

		var onScrollProgress = throttle(sendEvent, 500);

		document.addEventListener('scroll', onScrollProgress);

		var onScrollEnd = debounce(sendEvent, 1500);

		document.addEventListener('scroll', onScrollEnd);

		var onClick = event => {
			var element = event.target;
			var tagName = element.tagName.toLowerCase();

			var payload = {
				entryId,
				tagName
			};

			if (tagName === 'a') {
				payload.href = element.href;
				payload.text = element.innerText;
			}
			else if (tagName === 'img') {
				payload.src = element.src;
			}

			Analytics.send('CLICK', applicationId, payload);
		};

		entry.addEventListener('click', onClick);

		var onDestroyPortlet = function() {
			document.removeEventListener('scroll', onScrollProgress);
			document.removeEventListener('scroll', onScrollEnd);

			entry.removeEventListener('click', onClick);

			Liferay.detach('destroyPortlet', onDestroyPortlet);
		};

		Liferay.on('destroyPortlet', onDestroyPortlet);
	}
};