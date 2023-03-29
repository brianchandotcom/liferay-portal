@spira_Data_Source @Data_Source @Data_Transformation @team_FARO @priority_5 @blocked
Feature: Assert Data Transformation Summary
	As an Business User, I should be able to assert the data transformation summary page

	Background: [Setup] Upload a CSV Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "DataTransformationSummary - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,${Random.2}
			Opal.Henderson@gmail.com,Opal,Henderson,Economist,${Random.3}
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer,${Random.4}
			"""
		* I click the "CSV File" button
		* I browse for a file named "DataTransformationSummary - ${Random.1}.csv"
		* I name the Data Source "AddCSVDataSource - ${Random.1}.csv"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped

	Scenario: Fields Mapped
		Then I should see the correct icons for matched data model fields

	Scenario: Info Icon Disappears after Manual Match
		When I select "additionalName" for the SCV Data Model in row "5"
		Then I should not see the info icon in row "5"

	@prototype
	Scenario: View Info on Auto-Matched Transformations
		Then I should see a tooltip when I hover over the info icon in row "1"