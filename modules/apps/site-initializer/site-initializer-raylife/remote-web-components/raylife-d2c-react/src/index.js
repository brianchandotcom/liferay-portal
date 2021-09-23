import GetAQuote from './apps/get-a-quote/pages';
import SelectedQuote from './apps/selected-quote/pages';

if (!customElements.get(GetAQuote.tag)) {
	customElements.define(GetAQuote.tag, GetAQuote.class);
}

if (!customElements.get(SelectedQuote.tag)) {
	customElements.define(SelectedQuote.tag, SelectedQuote.class);
}
