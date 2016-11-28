import dom from 'metal-dom';
import State from 'metal-state/src/State';
import { async } from 'metal';

const CSS_CLASS_PLAYING = 'playing';

/**
 * MusicPlayer
 *
 * This class creates a basic component that enhances the default behaviour of the
 * songs with 1975 london theme
 */
class MusicPlayer extends State {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config) {
		super(opt_config);

		async.nextTick(() => this.bindUI_());
	}

	/**
	 * @inheritDoc
	 */
	disposeInternal() {
		if (this.albumClikHandle_) {
			this.albumClikHandle_.dispose();
		}

		super.disposeInternal();
	}

	/**
	 * Attaches the necessary event listeners of the component
	 * @protected
	 */
	bindUI_() {
		this.on('activeAlbumChanged', this.handleActiveAlbumChanged_);

		this.albumHandle_ = dom.delegate(
			document.body,
			'click',
			'.album',
			(event) => this.toggleElement_(event.delegateTarget)
		);
	}

	/**
	 * Handles the `activeAlbumChanged` event. Stops the previously being played
	 * album and starts the new one if any.
	 * @param  {!Object} event
	 * @protected
	 */
	handleActiveAlbumChanged_(event) {
		this.stopAlbum_(event.prevVal);
		this.playAlbum_(event.newVal);
	}

	/**
	 * Starts playing a given album element
	 * @param  {Object} album The album to be played
	 * @protected
	 */
	playAlbum_(album) {
		if (album) {
			album.audio.currentTime = 0;
			album.audio.play();

			dom.addClasses(album.element, CSS_CLASS_PLAYING);

			this.updateProgressBar_();
		}
	}

	/**
	 * Stops playing a given album element
	 * @param  {Object} album The album to be stopped
	 * @protected
	 */
	stopAlbum_(album) {
		if (album) {
			album.audio.pause();
			album.progressBar.style.width = '0%';

			dom.removeClasses(album.element, CSS_CLASS_PLAYING);
		}
	}

	/**
	 * Toggles an element between the play and paused states
	 * @param  {Element} element The element that received the user interaction
	 * @protected
	 */
	toggleElement_(element) {
		if (dom.hasClass(element, CSS_CLASS_PLAYING)) {
			this.activeAlbum = null;
		} else {
			this.activeAlbum = {
				audio: element.querySelector('audio'),
				element: element,
				progressBar: element.querySelector('.progress-bar')
			};
		}
	}

	/**
	 * Updates the progress bar with the currently playing state
	 * @protected
	 */
	updateProgressBar_() {
		if (this.activeAlbum) {
			var percent = Math.ceil((this.activeAlbum.audio.currentTime / this.activeAlbum.audio.duration) * 100);

			if (percent >= 100) {
				this.activeAlbum = null;
			} else if ( (window.requestAnimationFrame) ) {
				this.activeAlbum.progressBar.style.width = `${percent}%`;

				requestAnimationFrame(this.updateProgressBar_.bind(this));
			}
		}
	}
}

/**
 * State definition.
 * @ignore
 * @type {!Object}
 * @static
 */
MusicPlayer.STATE = {
	/**
	 * Album being played. Should have the following properties:
	 * - audio: The Audio element to be played
	 * - element: The container node element
	 * - progressBar: The node element to animate the progress bar
	 * @instance
	 * @memberof MusicPlayer
	 * @type {Object<Element, Element, Audio>}
	 */
	activeAlbum: {}
};

export default MusicPlayer;