package com.liferay.commerce.headless.product.apio.internal.form;

import com.liferay.apio.architect.form.Form;

import java.util.Date;

/**
 * @author Rodrigo Guedes de Souza
 */
public class ProductInstanceCreatorForm {

    public static Form<ProductInstanceCreatorForm> buildForm(
            Form.Builder<ProductInstanceCreatorForm> formBuilder) {

        return formBuilder.title(
                __ -> "The product instance creator form"
        ).description(
                __ -> "This form can be used to create a product instance"
        ).constructor(
                ProductInstanceCreatorForm::new
        ).addRequiredString(
                "sku", ProductInstanceCreatorForm::_setSku
        ).addOptionalString(
                "gtin", ProductInstanceCreatorForm::_setGtin
        ).addOptionalString(
                "manufacturerPartNumber", ProductInstanceCreatorForm::_setManufacturerPartNumber
        ).addOptionalBoolean(
                "purchasable", ProductInstanceCreatorForm::_setPurchasable
        ).addOptionalDouble(
                "width", ProductInstanceCreatorForm::_setWidth
        ).addOptionalDouble(
                "height", ProductInstanceCreatorForm::_setHeight
        ).addOptionalDouble(
                "depth", ProductInstanceCreatorForm::_setDepth
        ).addOptionalDouble(
                "weight", ProductInstanceCreatorForm::_setWeight
        ).addOptionalDouble(
                "cost", ProductInstanceCreatorForm::_setCost
        ).addOptionalDouble(
                "price", ProductInstanceCreatorForm::_setPrice
        ).addOptionalDouble(
                "promoPrice", ProductInstanceCreatorForm::_setPromoPrice
        ).addOptionalBoolean(
                "published", ProductInstanceCreatorForm::_setPublished
        ).addOptionalDate(
                "displayDate", ProductInstanceCreatorForm::_setDisplayDate
        ).addOptionalDate(
                "expirationDate", ProductInstanceCreatorForm::_setExpirationDate
        ).addOptionalBoolean(
                "neverExpire", ProductInstanceCreatorForm::_setNeverExpire
        ).build();
    }

    public String getSku() {
        return _sku;
    }

    public String getGtin() {
        return _gtin;
    }

    public String getManufacturerPartNumber() {
        return _manufacturerPartNumber;
    }

    public boolean getPurchasable() {
        return _purchasable;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDepth() {
        return _depth;
    }

    public double getWeight() {
        return _weight;
    }

    public double getCost() {
        return _cost;
    }

    public double getPrice() {
        return _price;
    }

    public double getPromoPrice() {
        return _promoPrice;
    }

    public boolean getPublished() {
        return _published;
    }

    public Date getDisplayDate() {
        return _displayDate;
    }

    public Date getExpirationDate() {
        return _expirationDate;
    }

    public boolean getNeverExpire() {
        return _neverExpire;
    }

    private void _setSku(String _sku) {
        this._sku = _sku;
    }

    private void _setGtin(String _gtin) {
        this._gtin = _gtin;
    }

    private void _setManufacturerPartNumber(String _manufacturerPartNumber) {
        this._manufacturerPartNumber = _manufacturerPartNumber;
    }

    private void _setPurchasable(boolean _purchasable) {
        this._purchasable = _purchasable;
    }

    private void _setWidth(double _width) {
        this._width = _width;
    }

    private void _setHeight(double _height) {
        this._height = _height;
    }

    private void _setDepth(double _depth) {
        this._depth = _depth;
    }

    private void _setWeight(double _weight) {
        this._weight = _weight;
    }

    private void _setCost(double _cost) {
        this._cost = _cost;
    }

    private void _setPrice(double _price) {
        this._price = _price;
    }

    private void _setPromoPrice(double _promoPrice) {
        this._promoPrice = _promoPrice;
    }

    private void _setPublished(boolean _published) {
        this._published = _published;
    }

    private void _setDisplayDate(Date _displayDate) {
        this._displayDate = _displayDate;
    }

    private void _setExpirationDate(Date _expirationDate) {
        this._expirationDate = _expirationDate;
    }

    private void _setNeverExpire(boolean _neverExpire) {
        this._neverExpire = _neverExpire;
    }

    private String _sku;
    private String _gtin;
    private String _manufacturerPartNumber;
    private boolean _purchasable;
    private double _width;
    private double _height;
    private double _depth;
    private double _weight;
    private double _cost;
    private double _price;
    private double _promoPrice;
    private boolean _published;
    private Date _displayDate;
    private Date _expirationDate;
    private boolean _neverExpire;

}