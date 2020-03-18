Simulate = {
	dragAndDrop: async function(source, target) {
		const dataTransfer = new DataTransfer();

		const dispatchEvent = async(type, element) => {
			const rect = element.getBoundingClientRect();

			const event = new DragEvent(
				type,
				{
					bubbles: true,
					clientX: rect.left + (rect.width / 2),
					clientY: rect.top + (rect.height / 2),
					dataTransfer: dataTransfer,
					relatedTarget: element
				});

			element.dispatchEvent(event);
		};

		await dispatchEvent('dragstart', source);

		await dispatchEvent('dragover', target);

		await dispatchEvent('drop', target);

		await dispatchEvent('dragend', source);
	}
};