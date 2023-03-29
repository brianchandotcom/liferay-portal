@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order Account Details
	As a Business User, I should be able to order an Account's details

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I search for "Lesch, Walsh and Stracke"
		* I click "Lesch, Walsh and Stracke" in the table
		* I click the "Details" tab

	Scenario: Order Account Details by Property Name
		When I sort the table by the "Attribute" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| accountId          |
			| accountName        |
			| accountType        |
			| annualRevenue      |
			| billingCity        |
			| billingCountry     |
			| billingPostalCode  |
			| billingState       |
			| billingStreet      |
			| currencyIsoCode    |
			| description        |
			| fax                |
			| industry           |
			| numberOfEmployees  |
			| ownership          |
			| phone              |
			| shippingCity       |
			| shippingCountry    |
			| shippingPostalCode |
			| shippingState      |
			| shippingStreet     |
			| website            |
			| yearStarted        |
		When I sort the table by the "Attribute" column header in descending order
		Then I should see the following ordered rows in column 1:
			| yearStarted        |
			| website            |
			| shippingStreet     |
			| shippingState      |
			| shippingPostalCode |
			| shippingCountry    |
			| shippingCity       |
			| phone              |
			| ownership          |
			| numberOfEmployees  |
			| industry           |
			| fax                |
			| description        |
			| currencyIsoCode    |
			| billingStreet      |
			| billingState       |
			| billingPostalCode  |
			| billingCountry     |
			| billingCity        |
			| annualRevenue      |
			| accountType        |
			| accountName        |
			| accountId          |

	@blocked
	Scenario: Order Account Details by Source Name
		And I sort the table by the "Source Name" column header in ascending order
		Then I should see the following ordered rows in column 2:
			| AnnualRevenue      |
			| BillingCity        |
			| BillingCountry     |
			| BillingPostalCode  |
			| BillingState       |
			| BillingStreet      |
			| CurrencyIsoCode    |
			| Description        |
			| Fax                |
			| id                 |
			| Industry           |
			| Name               |
			| NumberOfEmployees  |
			| Ownership          |
			| Phone              |
			| ShippingCity       |
			| ShippingCountry    |
			| ShippingPostalCode |
			| ShippingState      |
			| ShippingStreet     |
			| Type               |
			| Website            |
			| YearStarted        |
		And I sort the table by the "Source Name" column header in descending order
		Then I should see the following ordered rows in column 2:
			| YearStarted        |
			| Website            |
			| Type               |
			| ShippingStreet     |
			| ShippingState      |
			| ShippingPostalCode |
			| ShippingCountry    |
			| ShippingCity       |
			| Phone              |
			| Ownership          |
			| NumberOfEmployees  |
			| Name               |
			| Industry           |
			| id                 |
			| Fax                |
			| Description        |
			| CurrencyIsoCode    |
			| BillingStreet      |
			| BillingState       |
			| BillingPostalCode  |
			| BillingCountry     |
			| BillingCity        |
			| AnnualRevenue      |