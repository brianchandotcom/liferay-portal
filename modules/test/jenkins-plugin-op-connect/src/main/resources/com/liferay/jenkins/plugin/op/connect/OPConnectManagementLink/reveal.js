Behaviour.specify(
	"a.op-connect-reveal-token", "op-connect-reveal-token", 0,
	function (revealLink) {
		revealLink.onclick = function (event) {
			event.preventDefault();

			var accessTokenInput = document.getElementById("op-connect-token");

			if (accessTokenInput == null) {
				return;
			}

			if (accessTokenInput.type === "password") {
				accessTokenInput.type = "text";

				revealLink.textContent = "Hide";
			}
			else {
				accessTokenInput.type = "password";

				revealLink.textContent = "Show";
			}
		};
	}
);
