{
	const form = document.querySelector('.fragment-button:not(.parsed)');

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			alert('Form submitted');
		});
}