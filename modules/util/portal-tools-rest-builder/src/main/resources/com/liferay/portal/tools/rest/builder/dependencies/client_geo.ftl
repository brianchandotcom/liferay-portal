package ${configYAML.apiPackagePath}.client.custom.field;

import ${configYAML.apiPackagePath}.client.function.UnsafeSupplier;
import ${configYAML.apiPackagePath}.client.json.BaseJSONParser;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
* @author ${configYAML.author}
* @generated
*/
@Generated("")
public class Geo {

	public static Geo toDTO(String json) {
		GeoJSONParser geoJSONParser = new GeoJSONParser();

		return geoJSONParser.parseToDTO(json);
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLatitude(UnsafeSupplier<Double, Exception> latitudeUnsafeSupplier) {
		try {
			latitude = latitudeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double latitude;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(UnsafeSupplier<Double, Exception> longitudeUnsafeSupplier) {
		try {
			longitude = longitudeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double longitude;

	private static class GeoJSONParser extends BaseJSONParser<Geo> {

		@Override
		protected Geo createDTO() {
			return new Geo();
		}

		@Override
		protected Geo[] createDTOArray(int size) {
			return new Geo[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "latitude")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "longitude")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(Geo geo, String jsonParserFieldName, Object jsonParserFieldValue) {
			if (Objects.equals(jsonParserFieldName, "latitude")) {
				if (jsonParserFieldValue != null) {
					geo.setLatitude(Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "longitude")) {
				if (jsonParserFieldValue != null) {
					geo.setLongitude(Double.valueOf((String)jsonParserFieldValue));
				}
			}
		}
	}

}