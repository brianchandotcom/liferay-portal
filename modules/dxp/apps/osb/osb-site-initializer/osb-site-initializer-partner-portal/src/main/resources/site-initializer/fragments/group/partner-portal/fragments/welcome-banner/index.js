/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const preBanner = fragmentElement.querySelector('.pre-dismiss');
const posBanner = fragmentElement.querySelector('.pos-dismiss');
const button = fragmentElement.querySelector('#on-click-button');

fragmentElement.querySelector('#dismiss-button').onclick = () => {
	preBanner.classList.toggle('d-flex');
	preBanner.classList.toggle('d-none');

	posBanner.classList.toggle('d-none');
	posBanner.classList.toggle('d-flex');

	button.classList.toggle('d-none');
	button.classList.toggle('d-flex');
};

const videoButtons = document.querySelectorAll('.video-tour-button');

for (const videoButton of videoButtons) {
	videoButton.onclick = () =>
		Liferay.Util.openModal({
			size: 'lg',
			bodyHTML:
				'			<iframe width="100%" height="500" src="https://www.youtube.com/embed/bZeAh7dpskw" title="Liferay: One Platform for Endless Solutions" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>',
		});
}
