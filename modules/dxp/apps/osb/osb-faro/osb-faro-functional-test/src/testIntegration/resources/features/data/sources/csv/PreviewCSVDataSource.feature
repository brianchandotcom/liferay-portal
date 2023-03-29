@spira_Data_Source @Data_Source @CSV @team_FARO @priority_5 @blocked
Feature: Preview CSV Data Source
	As a Business User, I should be able to preview an uploaded CSV Data Source

	Background: [Setup] Navigate to the Data Source Page and Click to Add a Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button

	Scenario: Preview a CSV Data Source
		Given I create a file named "PreviewCSVDataSource - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			Opal.Henderson@gmail.com,Opal,Henderson,Economist
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer
			"""
		And I click the "CSV File" button
		And I browse for a file named "PreviewCSVDataSource - ${Random.1}.csv"
		And I name the Data Source "PreviewCSVDataSource - ${Random.1}.csv"
		And I click the "Next" button
		And I should see "4" fields mapped and "0" fields not mapped
		And I click the "Done" button
		And I should see that "PreviewCSVDataSource - ${Random.1}.csv" was successfully uploaded
		When I click the "View File Preview" button
		Then I should see the CSV data preview match the following table:
			| email                    | givenName  | familyName | jobTitle       |
			| Opal.Henderson@gmail.com | Opal       | Henderson  | Economist      |
			| Nettie.Morgan@gmail.com  | Nettie     | Morgan     | Civil Engineer |