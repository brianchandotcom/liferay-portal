@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4 @blocked
Feature: Create a Dynamic Individuals Segment with a criteria that uses is unknown
	As a Business User, I should be able to create a Dynamic Individuals Segment

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			${Random.2}@gmail.com,first${Random.2},last${Random.2},Economist
			${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}.csv"
		* I name the Data Source "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}" was successfully uploaded
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Create the Dynamic Individuals Segment using unknown
		Given I click the "Dynamic Segment" dropdown option
		And I select "Individual Attributes" from the criterion type dropdown
		When I create a criteria with the following conditions:
			| industry | is unknown |
		And I name the Dynamic segment "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}"
		Then I should see a "Segment" named "CreateDynamicIndividualSegmentIsUnknown - ${Random.1}" with "2" items