$(document).ready(function() {
	$(".expand-macro-steps > a").click(function () {
		if ($(this).parent().children('.macro-steps:first').is(":hidden")) {
			$(this).parent().children('.macro-steps:first').slideDown("fast");
		} else {
			$(this).parent().children('.macro-steps:first').slideUp("fast");
		}
		event.preventDefault();
	})	
});