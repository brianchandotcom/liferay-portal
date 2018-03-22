/* global Analytics */

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

		Analytics.registerPlugin(
			function(analytics) {
				document.addEventListener('scroll', throttle(sendEvent, 500));
				document.addEventListener('scroll', debounce(sendEvent, 1500));
			}
		);
	}
};