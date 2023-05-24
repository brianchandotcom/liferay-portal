<a class="copy-text ml-1 text-decoration-none" href="#copy-share-link" onclick="copyToClipboard(Liferay.ThemeDisplay.getLayoutURL())" style="align-items: center;color: #272833;cursor: pointer;display: flex;font-size: 18px;justify-content: space-between;text-decoration: none;">
	<svg class="link-icon mr-2" width="16" height="16" fill="none" xmlns="http://www.w3.org/2000/svg"><mask id="link" style="mask-type:alpha" maskUnits="userSpaceOnUse" x="1" y="4" width="14" height="8"><path fill-rule="evenodd" clip-rule="evenodd" d="M4.667 10h2c.366 0 .666.3.666.667 0 .367-.3.667-.666.667h-2a3.335 3.335 0 0 1 0-6.667h2c.366 0 .666.3.666.667 0 .366-.3.666-.666.666h-2c-1.1 0-2 .9-2 2s.9 2 2 2Zm6.666-5.333h-2c-.366 0-.666.3-.666.667 0 .366.3.666.666.666h2c1.1 0 2 .9 2 2s-.9 2-2 2h-2c-.366 0-.666.3-.666.667 0 .367.3.667.666.667h2A3.335 3.335 0 0 0 14.667 8a3.335 3.335 0 0 0-3.334-3.333ZM5.333 8c0 .367.3.667.667.667h4c.367 0 .667-.3.667-.667 0-.366-.3-.666-.667-.666H6c-.367 0-.667.3-.667.666Z" fill="#000"></path></mask><g mask="url(#link)"><path fill="#858c94" d="M0 0h16v16H0z"></path></g></svg>
	Copy &amp; Share Link
	<svg class="link-arrow" style="margin-left:auto;" width="16" height="16" fill="none" xmlns="http://www.w3.org/2000/svg"><mask id="arrow" style="mask-type:alpha" maskUnits="userSpaceOnUse" x="5" y="4" width="6" height="8"><path d="m6 10.584 2.587-2.587L6 5.41a.664.664 0 1 1 .94-.94L10 7.53c.26.26.26.68 0 .94l-3.06 3.06c-.26.26-.68.26-.94 0a.678.678 0 0 1 0-.946Z" fill="#000"></path></mask><g mask="url(#arrow)"><path fill="#858c94" d="M0 0h16v16H0z"></path></g></svg>
</a>

<script type="">
	var target = document.querySelector('[href="#copy-share-link"]');

	function copyToClipboard(text) {
		if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
			navigator.clipboard.writeText(text)
		};

		if (target) {
			target.popover('show');

			setTimeout(
				function() {
					target.popover('hide')
				},
				1000
			);
		}
	}
</script>