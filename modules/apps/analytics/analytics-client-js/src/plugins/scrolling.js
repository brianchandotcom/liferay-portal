import debounce from 'metal-debounce';
import {ScrollTracker} from '../utils/scroll';

/**
 * Plugin function that registers listener against scroll event
 * @param {object} analytics The Analytics client
 */
function scrolling(analytics) {
	let scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		scrollTracker.onDepthReached(depth => {
			analytics.send('depthReached', 'scrolling', {depth});
		});
	}, 1500);

	document.addEventListener('scroll', onScroll);

	// Reset levels on SPA-enabled environments

	const onLoad = () => {
		scrollTracker.dispose();
		scrollTracker = new ScrollTracker();
	};

	window.addEventListener('load', onLoad);

	return () => {
		document.removeEventListener('scroll', onScroll);
		window.removeEventListener('load', onLoad);
	};
}

export {scrolling};
export default scrolling;