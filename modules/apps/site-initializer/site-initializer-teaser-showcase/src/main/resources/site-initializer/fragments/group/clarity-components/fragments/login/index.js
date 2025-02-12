const tokens = location.href.split("/");
const groupKey = tokens[tokens.length-2];

const link = document.getElementById("homeLink");

link.href = link.href.replace("[$GROUP_KEY$]", groupKey);