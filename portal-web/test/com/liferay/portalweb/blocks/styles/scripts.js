$(document).ready(function() {
	$(".expand-macro-steps").click(function() {
		if ($(this).children().eq(1).is(":hidden")) {
			$(this).children().eq(1).slideDown("slow");
		} else {
			$(this).children().eq(1).slideUp();
		}
		event.preventDefault();
	})
});