@spira_Data_Source @Data_Source @CSV @team_FARO @priority_3 @blocked
Feature: CSV Data Source Form Validation
	As an Business User, I should not be able to create a CSV Data Source if the Data Source name is missing

	Background: [Setup] Navigate to Data Source Upload Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertCSVDataSourceFormValidation - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			Opal.Henderson@gmail.com,Opal,Henderson,Economist
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer
			"""

	Scenario: Empty Data Source Name Field
		Given I click the "CSV File" button
		When I browse for a file named "AssertCSVDataSourceFormValidation - ${Random.1}.csv"
		Then I should not be able to click the "Next" button
