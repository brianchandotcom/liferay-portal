/**
 * @memberof shared/components
 * @module controlMenuHeading
 * @description We need to do this component as temporary solution to manipulate the portal header bar'.
 * */
export default ({backPath, title}) => {
	let backMenu = null;
	const backMenuElement = document.getElementsByClassName(
		'metric-control-menu-heading'
	);
	const headerTitle = document.getElementsByClassName(
		'control-menu-level-1-heading'
	)[0];

	headerTitle.innerHTML = title;

	if (backMenuElement.length && !backPath) {
		backMenuElement[0].parentNode.removeChild(backMenuElement[0]);
	}
	else if (!backMenuElement.length) {
		const iconName = 'angle-left';
		const menuHeader = document.getElementsByClassName('control-menu-nav')[1];

		backMenu = document.createElement('li');
		backMenu.className = 'control-menu-nav-item metric-control-menu-heading';
		backMenu.innerHTML = `
			<a class="back-url-link control-menu-icon" href="javascript:history.back();" data-senna-off>
				<span class="icon-monospaced">
					<svg class="lexicon-icon lexicon-icon-${iconName}" focusable="false" role="presentation">
						<use href="${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg#${iconName}"/>
					</svg>
				</span>
			</a>`;

		menuHeader.appendChild(backMenu);
	}
	else {
		backMenu = backMenuElement[0];
		backMenu.setAttribute('data-path', backPath);
	}
};