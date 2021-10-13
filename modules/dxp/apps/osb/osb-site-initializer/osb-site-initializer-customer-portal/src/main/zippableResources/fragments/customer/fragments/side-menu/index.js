fragmentElement.addEventListener("click", function (event) {
  let items = fragmentElement.querySelectorAll(".menu-item-content");

  if (event.target.classList.contains("menu-item-content")) {
    event.target.classList.toggle("active");
  }

  items.forEach((f) => {
    if (f.classList.contains("active") != event.target) {
      if (f == event.target) {
      } else {
        f.classList.remove("active");
      }
    }
  });
  if (event.target.id == "subMenu") {
    let arrow = fragmentElement.querySelector(".arrow");
    let menu = fragmentElement.querySelector(".sub-menu");
 		arrow.classList.toggle("arrow-left");
    arrow.classList.toggle("arrow-down");
    menu.classList.toggle("sub-menu-closed");
    menu.classList.toggle("sub-menu-open");
  }
});