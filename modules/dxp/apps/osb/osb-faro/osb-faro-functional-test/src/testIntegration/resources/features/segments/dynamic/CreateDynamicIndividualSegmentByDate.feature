@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_4
Feature: Create a Dynamic Individuals Segment By Date
	As a Business User, I should be able to create a Dynamic Individuals Segment using a Date property

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Create the Dynamic Individuals Segment By Date
		Given I click the "Dynamic Segment" dropdown option
		And I select "Individual Attributes" from the criterion type dropdown
		When I create a criteria with the following Date condition:
			| birthDate | is after | September 14 2016 |
		And I name the Dynamic segment "CreateDynamicIndividualsSegmentByDate - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateDynamicIndividualsSegmentByDate - ${Random.1}"
		Then I should see a "Segment" named "CreateDynamicIndividualsSegmentByDate - ${Random.1}" with "12" items