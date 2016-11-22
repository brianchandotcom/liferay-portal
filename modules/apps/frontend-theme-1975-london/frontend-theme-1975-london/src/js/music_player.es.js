import async from 'metal/src/async/async';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import State from 'metal-state/src/State';

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
	constructor() {
		super();

		var instance = this;

		var body = dom.toElement(document.body);

		var updateProgressBarAnimation = function() {
			var percent = Math.floor((instance.activeAlbum.audio.currentTime / instance.activeAlbum.audio.duration) * 100);

			instance.activeAlbum.progressBar.style.width = percent + '%';

			if (window.requestAnimationFrame && percent < 100) {
				requestAnimationFrame(updateProgressBarAnimation);
			}

			if (percent === 100) {
				instance.resetAlbum_();
			}
		}

		dom.delegate(body, 'click', '.album', function(event) {

			var album = event.delegateTarget;

			if (album) {

				var audioAlbum = album.getElementsByTagName('audio')[0];

				var progressBar = album.getElementsByClassName('progress-bar')[0];

				if (dom.hasClass(album, 'playing')) {
					audioAlbum.pause();
					audioAlbum.currentTime = 0;
				}
				else {
					if (instance.activeAlbum) {
						instance.resetAlbum_();
					}

					audioAlbum.play();
					instance.activeAlbum = {
						album: album,
						audio: audioAlbum,
						progressBar: progressBar
					};

					if (window.requestAnimationFrame) {
						window.requestAnimationFrame(updateProgressBarAnimation);
					}
					else{
						instance.activeAlbum.audio.removeEventListener('timeupdate', updateProgressBarAnimation);
						instance.activeAlbum.audio.addEventListener('timeupdate', updateProgressBarAnimation);
					}
				}

				dom.toggleClasses(album, 'playing');
			}

		});
	}

	resetAlbum_() {
		this.activeAlbum.audio.pause();
		this.activeAlbum.audio.currentTime = 0;
		this.activeAlbum.progressBar.style.width = '0%';
		dom.removeClasses(this.activeAlbum.album, 'playing');
	}

}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
MusicPlayer.STATE = {
	activeAlbum: {}
};

export default MusicPlayer;