var links = document.querySelectorAll('.sign-in-form .taglib-icon');

for (var i = 0; i < links.length; i++) {
	links[i].addEventListener('click', function (event) {
		event.preventDefault();
		var currentNode = event.currentTarget;
		Liferay.Util.openModal({
			iframeBodyCssClass: 'login-modal',
			title: currentNode.text,
			url: currentNode.href,
		});
	});
}
