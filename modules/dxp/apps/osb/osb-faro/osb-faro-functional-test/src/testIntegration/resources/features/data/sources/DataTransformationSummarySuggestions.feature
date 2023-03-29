@spira_Data_Source @Data_Source @Data_Transformation @team_FARO @priority_5 @prototype
Feature: Data Transformation Summary Suggestions
	As an Business User, I should be able to have data source fields automapped to default suggestions

	Background: [Setup] Upload a CSV Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "DataTransformationSummary - ${Random.1}.csv" with the following content:
			"""
			Work Email,First Name,Last Name,Job Title
			Opal.Henderson@gmail.com,Opal,Henderson,Economist
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "DataTransformationSummarySuggestions - ${Random.1}.csv"
		* I name the Data Source "DataTransformationSummarySuggestions - ${Random.1}.csv"
		* I click the "Next" button

	Scenario: Fields Mapped
		Then I should see "4" fields mapped and "0" fields not mapped